/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.persistentattributes

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.document.ItemUtils
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest
import com.amazonaws.services.dynamodbv2.model.GetItemRequest
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement
import com.amazonaws.services.dynamodbv2.model.KeyType
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
import com.amazonaws.services.dynamodbv2.model.PutItemRequest
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType
import com.amazonaws.services.dynamodbv2.util.TableUtils
import com.hp.kalexa.core.persistence.PersistentAttributes
import com.hp.kalexa.core.persistence.exception.PersistentAttributesException
import java.util.Collections

class DynamoDbPersistentAttributes(
    private val tableName: String,
    private val partitionKeyName: String = DEFAULT_PARTITION_KEY_NAME,
    private val attributesKeyName: String = DEFAULT_ATTRIBUTES_KEY_NAME,
    private val autoCreateTable: Boolean = DEFAULT_AUTO_CREATE_TABLE,
    private val dynamoDb: AmazonDynamoDB = AmazonDynamoDBClientBuilder.standard().build()
) : PersistentAttributes {

    init {
        autoCreateTableIfNotExists()
    }

    @Throws(PersistentAttributesException::class)
    override fun getAttributes(partitionKey: String): Map<String, Any>? {
        val request: GetItemRequest = GetItemRequest()
            .withTableName(tableName)
            .withKey(Collections.singletonMap(partitionKeyName, AttributeValue().withS(partitionKey)))
            .withConsistentRead(true)
        val result: Map<String?, AttributeValue>? = try {
            dynamoDb.getItem(request).item
        } catch (e: ResourceNotFoundException) {
            throw PersistentAttributesException(
                "Table $tableName does not exist or is in the process of being created",
                e
            )
        } catch (e: AmazonDynamoDBException) {
            throw PersistentAttributesException("Failed to retrieve attributes from DynamoDB", e)
        }
        if (result != null && result.containsKey(attributesKeyName)) {
            return ItemUtils.toSimpleMapValue(result[attributesKeyName]?.m)
        }
        return null
    }

    @Throws(PersistentAttributesException::class)
    override fun saveAttributes(
        partitionKey: String,
        attributes: Map<String, Any>
    ) {
        val request: PutItemRequest = PutItemRequest()
            .withTableName(tableName)
            .withItem(getItem(partitionKey, attributes))
        try {
            dynamoDb.putItem(request)
        } catch (e: ResourceNotFoundException) {
            throw PersistentAttributesException(
                "Table $tableName does not exist or is in the process of being created", e
            )
        } catch (e: AmazonDynamoDBException) {
            throw PersistentAttributesException("Failed to save attributes to DynamoDB", e)
        }
    }

    private fun getItem(
        id: String,
        attributes: Map<String, Any>
    ): Map<String, AttributeValue> {
        return mutableMapOf(
            partitionKeyName to AttributeValue().withS(id),
            attributesKeyName to AttributeValue().withM(ItemUtils.fromSimpleMap(attributes))
        )
    }

    private fun autoCreateTableIfNotExists() {
        if (autoCreateTable) {
            val partitionKeyDefinition: AttributeDefinition = AttributeDefinition()
                .withAttributeName(partitionKeyName)
                .withAttributeType(ScalarAttributeType.S)
            val partitionKeySchema: KeySchemaElement = KeySchemaElement()
                .withAttributeName(partitionKeyName)
                .withKeyType(KeyType.HASH)
            val throughput: ProvisionedThroughput = ProvisionedThroughput()
                .withReadCapacityUnits(5L)
                .withWriteCapacityUnits(5L)
            try {
                TableUtils.createTableIfNotExists(
                    dynamoDb, CreateTableRequest()
                        .withTableName(tableName)
                        .withAttributeDefinitions(partitionKeyDefinition)
                        .withKeySchema(partitionKeySchema)
                        .withProvisionedThroughput(throughput)
                )
            } catch (e: AmazonDynamoDBException) {
                throw PersistentAttributesException("Create table request failed", e)
            }
        }
    }

    companion object {
        private const val DEFAULT_PARTITION_KEY_NAME = "id"
        private const val DEFAULT_ATTRIBUTES_KEY_NAME = "attributes"
        private const val DEFAULT_AUTO_CREATE_TABLE = false
    }
}
