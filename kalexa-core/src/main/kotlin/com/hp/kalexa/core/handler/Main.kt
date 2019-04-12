package com.hp.kalexa.core.handler

import com.hp.kalexa.core.annotation.Intent
import com.hp.kalexa.core.annotation.LaunchIntent
import com.hp.kalexa.core.handler.webapplication.AlexaWebApplication
import com.hp.kalexa.core.intent.IntentHandler
import com.hp.kalexa.model.request.AlexaRequest
import com.hp.kalexa.model.request.IntentRequest
import com.hp.kalexa.model.request.LaunchRequest

const val LAUNCH_REQUEST_JSON = """{
                                    "version": "1.0",
                                    "session": {
                                        "new": true,
                                        "sessionId": "amzn1.echo-api.session.9703cd9b-3f5a-4bb4-b6f0-46e1df10d108",
                                        "application": {
                                            "applicationId": "amzn1.ask.skill.dae46c69-85c5-4dbb-904f-fa1f61b34fce"
                                        },
                                        "user": {
                                            "userId": "amzn1.ask.account.AFIV6B5OL3F4KRCO2EJHSPVGDJ4KRGVPAVDB5UZPRAX6F5CNXLKDRSF4NKRF3NKVC2WUZLMKH5TKKNRJ5OPDNCUWPWCOWDR4J3BMSHK3ECAC54NIT67ETDC6KZSYEH4VMNNQG6QZWDFH7Z2GBV6LRRNAGQDNXICCDIMQITV6R36M5LEPIEI75B673BLZJX4JOIHVLWR4RURZ4RI",
                                            "permissions": {
                                                "consentToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjEifQ.eyJhdWQiOiJodHRwczovL2FwaS5hbWF6b25hbGV4YS5jb20iLCJpc3MiOiJBbGV4YVNraWxsS2l0Iiwic3ViIjoiYW16bjEuYXNrLnNraWxsLmRhZTQ2YzY5LTg1YzUtNGRiYi05MDRmLWZhMWY2MWIzNGZjZSIsImV4cCI6MTUyMjE2NjMwNSwiaWF0IjoxNTIyMTYyNzA1LCJuYmYiOjE1MjIxNjI3MDUsInByaXZhdGVDbGFpbXMiOnsiaXNEZXByZWNhdGVkIjoidHJ1ZSIsImNvbnNlbnRUb2tlbiI6IkF0emF8SXdFQklNRUg3UGQ0X0RKOTBnQjZobm5vWkJDVjFKVm1tRmdvX0gxcW9nTm1fZzd5UGhpQlo0dmh0amJVY3pGYjg5QnVCOTQzdmY2emtlaUJkVVRxdm1FM3ZBWmEwUXg5VlNYNUxRajByeVJ0alFBYXg4OG0zTFR5MERSSVVwZGJpeDBfQXZnS1RTNGgwOGVVeThfWVNDVmtKb2hYcVQzU0hYNjdCRzZFcW1ZTVhkaDMwdFBjOGVDSU9ySTNSaXZtNnFiOGpEMzk1LU1KbXdlZXNvMDI4VFpCc29PVlQ3MktjUFBwRnBvcjJhV0x0Q0xFN3RMcUk2ZWQyakZsWGZBYzRyNEJJX3k5elJXeDB5MDIyaXYzZFBJU2hhdktvVVV1T0lDcWRic2dVM3FBQW9ZZy1qVUQ0UW9TTnYwblUzTzZBUDFYMXFIT0dabHgyNkgySjdETHNFOWJfUGRlVVBRMjNaTHVDbmpubEZYNEFzY0FGZXc0UVJlVWhmYUQzUDNvWWxURXVTX0x0NC04Wndua2JPRmtRTEFOdndXQ0gtbkpXMDRxdkxlM21FTVJJREVKRU1jcWNXMmRTWktBN1R1QTRBSUVMU2JDc1ZlcHlVbmZ5SlI3QmVVNmpoYnk4bTBpbUp2ZlpkejdEdE1hNlNQRGc3YXlXM1JUY0djWG5EVnczREVTQWNCMlRmOHAyU1pTai1VMkdlSWhEV2pJIiwiZGV2aWNlSWQiOiJhbXpuMS5hc2suZGV2aWNlLkFIMlFKRTYyRlU3Vkk0VE1IWlVSM0RMWENJTlk0R0lJUkZXUTRISEREWE5YV0YzT1BaT1ZVVFlHU1daWEFHNlhLMlFPWExLUVdWUFNJVkRFUkkyTE9QT0ZUT0JLNU9FWUlBUzZBNUNTTFpEUFhYVTRONUFGWjVFRk9TVEZSWlNKRVM0QUcySU9VUzNXUjY1NVFCWExUVUpNVkhNQSIsInVzZXJJZCI6ImFtem4xLmFzay5hY2NvdW50LkFGSVY2QjVPTDNGNEtSQ08yRUpIU1BWR0RKNEtSR1ZQQVZEQjVVWlBSQVg2RjVDTlhMS0RSU0Y0TktSRjNOS1ZDMldVWkxNS0g1VEtLTlJKNU9QRE5DVVdQV0NPV0RSNEozQk1TSEszRUNBQzU0TklUNjdFVERDNktaU1lFSDRWTU5OUUc2UVpXREZIN1oyR0JWNkxSUk5BR1FETlhJQ0NESU1RSVRWNlIzNk01TEVQSUVJNzVCNjczQkxaSlg0Sk9JSFZMV1I0UlVSWjRSSSJ9fQ.ck69chFCou1wumWWQYhN1K7Q6i965ASau_1DLTaSkPFjT3E9DRcIoZooANLFyANo2QfCd44kRi995GqiSPIBDEccnbfePr8UAgdaeKmgLJcFXtf-5sNNk5HSVuFPgQ7UqPhUIREfNqLC3kS5Apcu0wETI8sEO1gkx9q_BoB8dsvzrmlj_32C7dxrWHDgIXS5UF913kDLH9XmNddM4yPPRe3KOejH6Fzy36koDZf2WGzUQQFMbz_vvDKnRrypi59aP_kRmS35BSXa8_amSgTxMnAkHTUEmHcG3TL50rpB2Ph4XcxSLdBcr_vK-q2bGxwes1XEx0EMSkGPo_9hPzoedA"
                                            }
                                        }
                                    },
                                    "context": {
                                        "AudioPlayer": {
                                            "playerActivity": "IDLE"
                                        },
                                        "Display": {
                                            "token": ""
                                        },
                                        "System": {
                                            "application": {
                                                "applicationId": "amzn1.ask.skill.dae46c69-85c5-4dbb-904f-fa1f61b34fce"
                                            },
                                            "user": {
                                                "userId": "amzn1.ask.account.AFIV6B5OL3F4KRCO2EJHSPVGDJ4KRGVPAVDB5UZPRAX6F5CNXLKDRSF4NKRF3NKVC2WUZLMKH5TKKNRJ5OPDNCUWPWCOWDR4J3BMSHK3ECAC54NIT67ETDC6KZSYEH4VMNNQG6QZWDFH7Z2GBV6LRRNAGQDNXICCDIMQITV6R36M5LEPIEI75B673BLZJX4JOIHVLWR4RURZ4RI",
                                                "permissions": {
                                                    "consentToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjEifQ.eyJhdWQiOiJodHRwczovL2FwaS5hbWF6b25hbGV4YS5jb20iLCJpc3MiOiJBbGV4YVNraWxsS2l0Iiwic3ViIjoiYW16bjEuYXNrLnNraWxsLmRhZTQ2YzY5LTg1YzUtNGRiYi05MDRmLWZhMWY2MWIzNGZjZSIsImV4cCI6MTUyMjE2NjMwNSwiaWF0IjoxNTIyMTYyNzA1LCJuYmYiOjE1MjIxNjI3MDUsInByaXZhdGVDbGFpbXMiOnsiaXNEZXByZWNhdGVkIjoidHJ1ZSIsImNvbnNlbnRUb2tlbiI6IkF0emF8SXdFQklNRUg3UGQ0X0RKOTBnQjZobm5vWkJDVjFKVm1tRmdvX0gxcW9nTm1fZzd5UGhpQlo0dmh0amJVY3pGYjg5QnVCOTQzdmY2emtlaUJkVVRxdm1FM3ZBWmEwUXg5VlNYNUxRajByeVJ0alFBYXg4OG0zTFR5MERSSVVwZGJpeDBfQXZnS1RTNGgwOGVVeThfWVNDVmtKb2hYcVQzU0hYNjdCRzZFcW1ZTVhkaDMwdFBjOGVDSU9ySTNSaXZtNnFiOGpEMzk1LU1KbXdlZXNvMDI4VFpCc29PVlQ3MktjUFBwRnBvcjJhV0x0Q0xFN3RMcUk2ZWQyakZsWGZBYzRyNEJJX3k5elJXeDB5MDIyaXYzZFBJU2hhdktvVVV1T0lDcWRic2dVM3FBQW9ZZy1qVUQ0UW9TTnYwblUzTzZBUDFYMXFIT0dabHgyNkgySjdETHNFOWJfUGRlVVBRMjNaTHVDbmpubEZYNEFzY0FGZXc0UVJlVWhmYUQzUDNvWWxURXVTX0x0NC04Wndua2JPRmtRTEFOdndXQ0gtbkpXMDRxdkxlM21FTVJJREVKRU1jcWNXMmRTWktBN1R1QTRBSUVMU2JDc1ZlcHlVbmZ5SlI3QmVVNmpoYnk4bTBpbUp2ZlpkejdEdE1hNlNQRGc3YXlXM1JUY0djWG5EVnczREVTQWNCMlRmOHAyU1pTai1VMkdlSWhEV2pJIiwiZGV2aWNlSWQiOiJhbXpuMS5hc2suZGV2aWNlLkFIMlFKRTYyRlU3Vkk0VE1IWlVSM0RMWENJTlk0R0lJUkZXUTRISEREWE5YV0YzT1BaT1ZVVFlHU1daWEFHNlhLMlFPWExLUVdWUFNJVkRFUkkyTE9QT0ZUT0JLNU9FWUlBUzZBNUNTTFpEUFhYVTRONUFGWjVFRk9TVEZSWlNKRVM0QUcySU9VUzNXUjY1NVFCWExUVUpNVkhNQSIsInVzZXJJZCI6ImFtem4xLmFzay5hY2NvdW50LkFGSVY2QjVPTDNGNEtSQ08yRUpIU1BWR0RKNEtSR1ZQQVZEQjVVWlBSQVg2RjVDTlhMS0RSU0Y0TktSRjNOS1ZDMldVWkxNS0g1VEtLTlJKNU9QRE5DVVdQV0NPV0RSNEozQk1TSEszRUNBQzU0TklUNjdFVERDNktaU1lFSDRWTU5OUUc2UVpXREZIN1oyR0JWNkxSUk5BR1FETlhJQ0NESU1RSVRWNlIzNk01TEVQSUVJNzVCNjczQkxaSlg0Sk9JSFZMV1I0UlVSWjRSSSJ9fQ.ck69chFCou1wumWWQYhN1K7Q6i965ASau_1DLTaSkPFjT3E9DRcIoZooANLFyANo2QfCd44kRi995GqiSPIBDEccnbfePr8UAgdaeKmgLJcFXtf-5sNNk5HSVuFPgQ7UqPhUIREfNqLC3kS5Apcu0wETI8sEO1gkx9q_BoB8dsvzrmlj_32C7dxrWHDgIXS5UF913kDLH9XmNddM4yPPRe3KOejH6Fzy36koDZf2WGzUQQFMbz_vvDKnRrypi59aP_kRmS35BSXa8_amSgTxMnAkHTUEmHcG3TL50rpB2Ph4XcxSLdBcr_vK-q2bGxwes1XEx0EMSkGPo_9hPzoedA"
                                                }
                                            },
                                            "device": {
                                                "deviceId": "amzn1.ask.device.AH2QJE62FU7VI4TMHZUR3DLXCINY4GIIRFWQ4HHDDXNXWF3OPZOVUTYGSWZXAG6XK2QOXLKQWVPSIVDERI2LOPOFTOBK5OEYIAS6A5CSLZDPXXU4N5AFZ5EFOSTFRZSJES4AG2IOUS3WR655QBXLTUJMVHMA",
                                                "supportedInterfaces": {
                                                    "AudioPlayer": {},
                                                    "Display": {
                                                        "templateVersion": "1.0",
                                                        "markupVersion": "1.0"
                                                    }
                                                }
                                            },
                                            "apiEndpoint": "https://api.amazonalexa.com",
                                            "apiAccessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjEifQ.eyJhdWQiOiJodHRwczovL2FwaS5hbWF6b25hbGV4YS5jb20iLCJpc3MiOiJBbGV4YVNraWxsS2l0Iiwic3ViIjoiYW16bjEuYXNrLnNraWxsLmRhZTQ2YzY5LTg1YzUtNGRiYi05MDRmLWZhMWY2MWIzNGZjZSIsImV4cCI6MTUyMjE2NjMwNSwiaWF0IjoxNTIyMTYyNzA1LCJuYmYiOjE1MjIxNjI3MDUsInByaXZhdGVDbGFpbXMiOnsiY29uc2VudFRva2VuIjoiQXR6YXxJd0VCSU1FSDdQZDRfREo5MGdCNmhubm9aQkNWMUpWbW1GZ29fSDFxb2dObV9nN3lQaGlCWjR2aHRqYlVjekZiODlCdUI5NDN2ZjZ6a2VpQmRVVHF2bUUzdkFaYTBReDlWU1g1TFFqMHJ5UnRqUUFheDg4bTNMVHkwRFJJVXBkYml4MF9BdmdLVFM0aDA4ZVV5OF9ZU0NWa0pvaFhxVDNTSFg2N0JHNkVxbVlNWGRoMzB0UGM4ZUNJT3JJM1Jpdm02cWI4akQzOTUtTUptd2Vlc28wMjhUWkJzb09WVDcyS2NQUHBGcG9yMmFXTHRDTEU3dExxSTZlZDJqRmxYZkFjNHI0QklfeTl6Uld4MHkwMjJpdjNkUElTaGF2S29VVXVPSUNxZGJzZ1UzcUFBb1lnLWpVRDRRb1NOdjBuVTNPNkFQMVgxcUhPR1pseDI2SDJKN0RMc0U5Yl9QZGVVUFEyM1pMdUNuam5sRlg0QXNjQUZldzRRUmVVaGZhRDNQM29ZbFRFdVNfTHQ0LThad25rYk9Ga1FMQU52d1dDSC1uSlcwNHF2TGUzbUVNUklERUpFTWNxY1cyZFNaS0E3VHVBNEFJRUxTYkNzVmVweVVuZnlKUjdCZVU2amhieThtMGltSnZmWmR6N0R0TWE2U1BEZzdheVczUlRjR2NYbkRWdzNERVNBY0IyVGY4cDJTWlNqLVUyR2VJaERXakkiLCJkZXZpY2VJZCI6ImFtem4xLmFzay5kZXZpY2UuQUgyUUpFNjJGVTdWSTRUTUhaVVIzRExYQ0lOWTRHSUlSRldRNEhIRERYTlhXRjNPUFpPVlVUWUdTV1pYQUc2WEsyUU9YTEtRV1ZQU0lWREVSSTJMT1BPRlRPQks1T0VZSUFTNkE1Q1NMWkRQWFhVNE41QUZaNUVGT1NURlJaU0pFUzRBRzJJT1VTM1dSNjU1UUJYTFRVSk1WSE1BIiwidXNlcklkIjoiYW16bjEuYXNrLmFjY291bnQuQUZJVjZCNU9MM0Y0S1JDTzJFSkhTUFZHREo0S1JHVlBBVkRCNVVaUFJBWDZGNUNOWExLRFJTRjROS1JGM05LVkMyV1VaTE1LSDVUS0tOUko1T1BETkNVV1BXQ09XRFI0SjNCTVNISzNFQ0FDNTROSVQ2N0VUREM2S1pTWUVINFZNTk5RRzZRWldERkg3WjJHQlY2TFJSTkFHUUROWElDQ0RJTVFJVFY2UjM2TTVMRVBJRUk3NUI2NzNCTFpKWDRKT0lIVkxXUjRSVVJaNFJJIn19.AnIhysic4gn4ggpd64jOo_3xU1w1u6mYuGsaRlt-FdM4s3aw7P2wRsSs1SnvNkhN5ZNwHqZTpJVu4Zf22gKUyVN3f8CwRaBY6SkVzvr2rYLd5r2kvdkzoZrJnI1ZBIDa9j20giJNkEY4n-JOaNkJ6EPlZam59qQy2XRRsjAgiVDjiF3GtAZpuyELJ-Kf0hZFmQDhWuqAIDZCp9VhJ7YmL5hdDBT4pDuVpkq7QbYoV7ShV3bOrRq5cXl1sljDXbDaG7RYwBbhfOz7Z19RkuNFU7u02hZQHiq_BMe4KQGSE6kKeUCdYp7SMg1hOyWTDjua5ty15CJwRDnJknJcFlqpDw"
                                        }
                                    },
                                    "request": {
                                        "type": "LaunchRequest",
                                        "requestId": "amzn1.echo-api.request.64960f63-5dbb-451e-928b-f62b0d5d22db",
                                        "timestamp": "2018-03-27T14:58:25Z",
                                        "locale": "en-US"
                                    }
                                }"""

const val INTENT_REQUEST_JSON = """{
                                "version": "1.0",
                                "session": {
                                    "new": false,
                                    "sessionId": "amzn1.echo-api.session.3231e1ff-65d1-40b0-af7a-f42c01da4673",
                                    "application": {
                                        "applicationId": "amzn1.ask.skill.f400c9e3-37c6-44e5-9e58-3c5b9bd2c74e"
                                    },
                                    "attributes": {
                                        "action": "MailingLabelIntent"
                                    },
                                    "user": {
                                        "userId": "amzn1.ask.account.AH65RHGH2MXZJ5UMTSPZIRJK7BTSDV5MYLPD5CQOMSSD3CVMONORSWYLG3HT3HVLJNPD2KTW626W5K7ZWGJTH6YIG6SSG4WQ2UO2MDTB3LYEUM4P6VPXNYPQJLPIERS354K5X6SBEIUQZP7Y6BOOG4RDJIZB6BN35UER4RWLA2IFIHZWTPFV3FZDBTTRQ6RVNMYL4O2TLPDRWKQ"
                                    }
                                },
                                "context": {
                                    "AudioPlayer": {
                                        "playerActivity": "IDLE"
                                    },
                                    "System": {
                                        "application": {
                                            "applicationId": "amzn1.ask.skill.f400c9e3-37c6-44e5-9e58-3c5b9bd2c74e"
                                        },
                                        "user": {
                                            "userId": "amzn1.ask.account.AH65RHGH2MXZJ5UMTSPZIRJK7BTSDV5MYLPD5CQOMSSD3CVMONORSWYLG3HT3HVLJNPD2KTW626W5K7ZWGJTH6YIG6SSG4WQ2UO2MDTB3LYEUM4P6VPXNYPQJLPIERS354K5X6SBEIUQZP7Y6BOOG4RDJIZB6BN35UER4RWLA2IFIHZWTPFV3FZDBTTRQ6RVNMYL4O2TLPDRWKQ"
                                        },
                                        "device": {
                                            "deviceId": "amzn1.ask.device.AF53A3NPQZTFPSJBCPSWRSS3PYJ4EYF2Y3P3TJ5T4U7WSCLRQ3G6JNEGCAAK5YJB6H2PYGM3B65U7XZ7WGOUTIEMN4ER3WU6BRAJRVVPJNVHXDTR75QH5BZF7GKD4TZR3OQ36GSL5JZH67PFC74GM7RIYKOQ",
                                            "supportedInterfaces": {
                                                "AudioPlayer": {}
                                            }
                                        },
                                        "apiEndpoint": "https://api.amazonalexa.com",
                                        "apiAccessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjEifQ.eyJhdWQiOiJodHRwczovL2FwaS5hbWF6b25hbGV4YS5jb20iLCJpc3MiOiJBbGV4YVNraWxsS2l0Iiwic3ViIjoiYW16bjEuYXNrLnNraWxsLmY0MDBjOWUzLTM3YzYtNDRlNS05ZTU4LTNjNWI5YmQyYzc0ZSIsImV4cCI6MTUxOTMxNzE1NCwiaWF0IjoxNTE5MzEzNTU0LCJuYmYiOjE1MTkzMTM1NTQsInByaXZhdGVDbGFpbXMiOnsiY29uc2VudFRva2VuIjpudWxsLCJkZXZpY2VJZCI6ImFtem4xLmFzay5kZXZpY2UuQUY1M0EzTlBRWlRGUFNKQkNQU1dSU1MzUFlKNEVZRjJZM1AzVEo1VDRVN1dTQ0xSUTNHNkpORUdDQUFLNVlKQjZIMlBZR00zQjY1VTdYWjdXR09VVElFTU40RVIzV1U2QlJBSlJWVlBKTlZIWERUUjc1UUg1QlpGN0dLRDRUWlIzT1EzNkdTTDVKWkg2N1BGQzc0R003UklZS09RIiwidXNlcklkIjoiYW16bjEuYXNrLmFjY291bnQuQUg2NVJIR0gyTVhaSjVVTVRTUFpJUkpLN0JUU0RWNU1ZTFBENUNRT01TU0QzQ1ZNT05PUlNXWUxHM0hUM0hWTEpOUEQyS1RXNjI2VzVLN1pXR0pUSDZZSUc2U1NHNFdRMlVPMk1EVEIzTFlFVU00UDZWUFhOWVBRSkxQSUVSUzM1NEs1WDZTQkVJVVFaUDdZNkJPT0c0UkRKSVpCNkJOMzVVRVI0UldMQTJJRklIWldUUEZWM0ZaREJUVFJRNlJWTk1ZTDRPMlRMUERSV0tRIn19.brwoDjQZCptUDMpvptN9eAcXPcgInJnpDQxyI1qpQoW7StKXMP62wDyKNzqo9mE2l5uJeWdLIzD2ArZa1UrmU4VDAvXJo7ZDNtsnCHMh3gMUoWVisbnL5FwtKUYZ9nnNAjPbJ-NQLh65XNJqrb_TlFtuxUzNPUV63N0Dssn4rRfsjDKpocvCVodbIJSScoIH08LyWR97SI80R5qzOVNrBfdUccRSiihm4yptnE3OARPjhPqbCNk5C5IkMOF2ljZgBeg54SZOPT3HuxbTJChXYtxRMJvFvLQLjHVzCI3B7lE4ABiAwlxt2DeoZMKdkPaeZFAJMF2JX3gh7n83sPoaJw"
                                    }
                                },
                                "request": {
                                    "type": "IntentRequest",
                                    "requestId": "amzn1.echo-api.request.78b71dd4-7c67-4b68-aaef-6547a2e0f12d",
                                    "timestamp": "2018-02-22T15:32:34Z",
                                    "locale": "en-US",
                                    "intent": {
                                        "name": "MailingLabelIntent",
                                        "confirmationStatus": "NONE",
                                        "slots": {
                                            "product": {
                                                "name": "product",
                                                "value": "jersey",
                                                "resolutions": {
                                                    "resolutionsPerAuthority": [
                                                        {
                                                            "authority": "amzn1.er-authority.echo-sdk.amzn1.ask.skill.f400c9e3-37c6-44e5-9e58-3c5b9bd2c74e.HP_PRODUCT",
                                                            "status": {
                                                                "code": "ER_SUCCESS_MATCH"
                                                            },
                                                            "values": [
                                                                {
                                                                    "value": {
                                                                        "name": "Internacional Jersey",
                                                                        "id": "5682b7c3141702ac3311f58686428eeb"
                                                                    }
                                                                }
                                                            ]
                                                        }
                                                    ]
                                                },
                                                "confirmationStatus": "NONE"
                                            }
                                        }
                                    },
                                    "dialogState": "IN_PROGRESS"
                                }
                            }"""

fun main(args: Array<String>) {


    val handlers: List<IntentHandler> = listOf(FakeHandler(), FakeHandler(), FakeHandler())
    val interceptors: List<RequestInterceptor<*>> = listOf(IntentRequestInterceptor(), IntentRequestInterceptor())
    val skillConfig = SkillConfig(intentHandlers = handlers, interceptors = interceptors)
    val alexaWebApplication = AlexaWebApplication(skillConfig)
    alexaWebApplication.process(LAUNCH_REQUEST_JSON)
}

@Intent
class FakeHandler : IntentHandler
class IntentRequestInterceptor : RequestInterceptor<IntentRequest> {
    override fun intercept(alexaRequest: AlexaRequest<IntentRequest>) {
        alexaRequest.request
        println("***********************" + alexaRequest.request.intent)
    }
}
@LaunchIntent
class LaunchRequestInterceptor: RequestInterceptor<LaunchRequest> {
    override fun intercept(alexaRequest: AlexaRequest<LaunchRequest>) {
        println("-------------------------" + alexaRequest.request.timestamp)
    }

}
