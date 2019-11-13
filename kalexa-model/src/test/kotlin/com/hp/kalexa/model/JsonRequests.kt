/*
 * Copyright 2018 HP Development Company, L.P.
 * SPDX-License-Identifier: MIT
 */

package com.hp.kalexa.model

object JsonRequests {
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
    const val WEB_PAGE_SESSION_RESUMED_REQUEST = """{
                    "version": "1.0",
                    "session": {
                        "new": true,
                        "sessionId": "amzn1.echo-api.session.b6ee4cc0-658a-44de-8126-6a2a2e851437",
                        "application": {
                            "applicationId": "amzn1.ask.skill.f400c9e3-37c6-44e5-9e58-3c5b9bd2c74e"
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
                                    "AudioPlayer": {},
                                    "Display": {
                                        "templateVersion": "1.0",
                                        "markupVersion": "1.0"
                                    }
                                 }
                             },
                            "apiEndpoint": "https://api.amazonalexa.com",
                            "apiAccessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjEifQ.eyJhdWQiOiJodHRwczovL2FwaS5hbWF6b25hbGV4YS5jb20iLCJpc3MiOiJBbGV4YVNraWxsS2l0Iiwic3ViIjoiYW16bjEuYXNrLnNraWxsLmY0MDBjOWUzLTM3YzYtNDRlNS05ZTU4LTNjNWI5YmQyYzc0ZSIsImV4cCI6MTUxOTMxNTIzNywiaWF0IjoxNTE5MzExNjM3LCJuYmYiOjE1MTkzMTE2MzcsInByaXZhdGVDbGFpbXMiOnsiY29uc2VudFRva2VuIjpudWxsLCJkZXZpY2VJZCI6ImFtem4xLmFzay5kZXZpY2UuQUY1M0EzTlBRWlRGUFNKQkNQU1dSU1MzUFlKNEVZRjJZM1AzVEo1VDRVN1dTQ0xSUTNHNkpORUdDQUFLNVlKQjZIMlBZR00zQjY1VTdYWjdXR09VVElFTU40RVIzV1U2QlJBSlJWVlBKTlZIWERUUjc1UUg1QlpGN0dLRDRUWlIzT1EzNkdTTDVKWkg2N1BGQzc0R003UklZS09RIiwidXNlcklkIjoiYW16bjEuYXNrLmFjY291bnQuQUg2NVJIR0gyTVhaSjVVTVRTUFpJUkpLN0JUU0RWNU1ZTFBENUNRT01TU0QzQ1ZNT05PUlNXWUxHM0hUM0hWTEpOUEQyS1RXNjI2VzVLN1pXR0pUSDZZSUc2U1NHNFdRMlVPMk1EVEIzTFlFVU00UDZWUFhOWVBRSkxQSUVSUzM1NEs1WDZTQkVJVVFaUDdZNkJPT0c0UkRKSVpCNkJOMzVVRVI0UldMQTJJRklIWldUUEZWM0ZaREJUVFJRNlJWTk1ZTDRPMlRMUERSV0tRIn19.bcroBb51emiLmgyPDnhX120thX22Zp7NgR8IwrgMxWLRJnYwjgtaFxki4jonnHAPzA_Err5AJoYzq242u0elrmkCOFQ6uAeatrgf2Ekw_692dS5P-7LA8YKsdQ2LF5AeW8q5J8hin1GWDH-fDpsmkMHm9JsN1R1eTNFxT2fO3OoFV7KAbarpm_i7-8Of9NDbAjQ7RZdfycrB3-4pxnXx8sEQxS64AftYb5QZh5-ZXVlw6NGam5KQehp6go8bd4gslbVzbFzixs8TYZQqEV-zpsdVO18xKxwwnmDJlvrP1bjxPwjgKajaZwHyxm_kXy_X7PEqOn-MF4aOwcH_Wclw4w"
                        }
                    },
                    "request": {
                        "type": "SessionResumedRequest",
                        "requestId": "amzn1.echo-api.request.2c940401-13f2-4224-82a7-c7b2276c1523",
                        "timestamp": "2018-02-22T15:00:37Z",
                        "locale": "en-US",
                        "originIpAddress": "string",
                        "cause": {
                            "type": "ConnectionCompleted",
                            "token": "1234",
                            "status": {
                                "code": "200",
                                "message": "OK"
                            },
                            "result": null
                        }
                    }
                }"""

    const val CONNECTIONS_REQUEST = """{
                    "version": "1.0",
                    "session": {
                        "new": true,
                        "sessionId": "amzn1.echo-api.session.b6ee4cc0-658a-44de-8126-6a2a2e851437",
                        "application": {
                            "applicationId": "amzn1.ask.skill.f400c9e3-37c6-44e5-9e58-3c5b9bd2c74e"
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
                                    "AudioPlayer": {},
                                    "Display": {
                                        "templateVersion": "1.0",
                                        "markupVersion": "1.0"
                                    }
                                 }
                             },
                            "apiEndpoint": "https://api.amazonalexa.com",
                            "apiAccessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjEifQ.eyJhdWQiOiJodHRwczovL2FwaS5hbWF6b25hbGV4YS5jb20iLCJpc3MiOiJBbGV4YVNraWxsS2l0Iiwic3ViIjoiYW16bjEuYXNrLnNraWxsLmY0MDBjOWUzLTM3YzYtNDRlNS05ZTU4LTNjNWI5YmQyYzc0ZSIsImV4cCI6MTUxOTMxNTIzNywiaWF0IjoxNTE5MzExNjM3LCJuYmYiOjE1MTkzMTE2MzcsInByaXZhdGVDbGFpbXMiOnsiY29uc2VudFRva2VuIjpudWxsLCJkZXZpY2VJZCI6ImFtem4xLmFzay5kZXZpY2UuQUY1M0EzTlBRWlRGUFNKQkNQU1dSU1MzUFlKNEVZRjJZM1AzVEo1VDRVN1dTQ0xSUTNHNkpORUdDQUFLNVlKQjZIMlBZR00zQjY1VTdYWjdXR09VVElFTU40RVIzV1U2QlJBSlJWVlBKTlZIWERUUjc1UUg1QlpGN0dLRDRUWlIzT1EzNkdTTDVKWkg2N1BGQzc0R003UklZS09RIiwidXNlcklkIjoiYW16bjEuYXNrLmFjY291bnQuQUg2NVJIR0gyTVhaSjVVTVRTUFpJUkpLN0JUU0RWNU1ZTFBENUNRT01TU0QzQ1ZNT05PUlNXWUxHM0hUM0hWTEpOUEQyS1RXNjI2VzVLN1pXR0pUSDZZSUc2U1NHNFdRMlVPMk1EVEIzTFlFVU00UDZWUFhOWVBRSkxQSUVSUzM1NEs1WDZTQkVJVVFaUDdZNkJPT0c0UkRKSVpCNkJOMzVVRVI0UldMQTJJRklIWldUUEZWM0ZaREJUVFJRNlJWTk1ZTDRPMlRMUERSV0tRIn19.bcroBb51emiLmgyPDnhX120thX22Zp7NgR8IwrgMxWLRJnYwjgtaFxki4jonnHAPzA_Err5AJoYzq242u0elrmkCOFQ6uAeatrgf2Ekw_692dS5P-7LA8YKsdQ2LF5AeW8q5J8hin1GWDH-fDpsmkMHm9JsN1R1eTNFxT2fO3OoFV7KAbarpm_i7-8Of9NDbAjQ7RZdfycrB3-4pxnXx8sEQxS64AftYb5QZh5-ZXVlw6NGam5KQehp6go8bd4gslbVzbFzixs8TYZQqEV-zpsdVO18xKxwwnmDJlvrP1bjxPwjgKajaZwHyxm_kXy_X7PEqOn-MF4aOwcH_Wclw4w"
                        }
                    },
                    "request": {
                        "type": "LaunchRequest",
                        "requestId": "amzn1.echo-api.request.2c940401-13f2-4224-82a7-c7b2276c1523",
                        "timestamp": "2018-02-22T15:00:37Z",
                        "locale": "en-US",
                        "originIpAddress": "string",
                        "task": {
                            "name": "AMAZON.PrintPDF",
                            "version": "1",
                            "input": {
                                "@type": "PrintPDFRequest",
                                "@version": "1",
                                "title" : "Mac & Cheese",
                                "description" : "This is a nice rich mac and cheese. Serve with a salad for a great meatless dinner. Hope you enjoy it",
                                "url": "http://allrecipes.com/recipe/11679/homemade-mac-and-cheese/"
                            }
                        }
                    }
                }"""

    const val ERROR_LINK_RESULT = """{
                                    "version": "1.0",
                                    "session": {
                                        "new": true,
                                        "sessionId": "amzn1.echo-api.session.4c62c110-4038-4274-9de9-3cff0a61fbbb",
                                        "application": {
                                            "applicationId": "amzn1.ask.skill.f400c9e3-37c6-44e5-9e58-3c5b9bd2c74e"
                                        },
                                        "user": {
                                            "userId": "amzn1.ask.account.AHOQRHVEJVZJMPIOXOWODWC33EJLQZ3JLYGBRYUB66YX25FQZQ677JJZFNJVDVC2OTEEPD4J4C3FNKO3TEKDIIYH2LPJHJ2LL6IMBJUFV5OLPEEBNI3W5J3N3IY4IZESLKVUMNIHCFJ7Y6DGYRX24ELDPEK5M4XNLWTZWK5EKU3SUZ4G4XQUJOGGAL33P224IBBVCTMBNXRWJMI"
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
                                                "applicationId": "amzn1.ask.skill.f400c9e3-37c6-44e5-9e58-3c5b9bd2c74e"
                                            },
                                            "user": {
                                                "userId": "amzn1.ask.account.AHOQRHVEJVZJMPIOXOWODWC33EJLQZ3JLYGBRYUB66YX25FQZQ677JJZFNJVDVC2OTEEPD4J4C3FNKO3TEKDIIYH2LPJHJ2LL6IMBJUFV5OLPEEBNI3W5J3N3IY4IZESLKVUMNIHCFJ7Y6DGYRX24ELDPEK5M4XNLWTZWK5EKU3SUZ4G4XQUJOGGAL33P224IBBVCTMBNXRWJMI"
                                            },
                                            "device": {
                                                "deviceId": "amzn1.ask.device.AGUAQTHFDO6BK7UB43UQVQDHHD4WU4H2AUHKK5SCBK6ONWQODSDXV4BQGUL6EVTS27XTQQUYBSEEZQ5J6SDWZRZ6PBXRW2DBWRAQH4WOGUAERDPD7OAKIQ76YL2KUAYZFTLKIBJTDVWCSNBQIWPRICPN6E3Q",
                                                "supportedInterfaces": {
                                                    "AudioPlayer": {},
                                                    "Display": {
                                                        "templateVersion": "1.0",
                                                        "markupVersion": "1.0"
                                                    }
                                                }
                                            },
                                            "apiEndpoint": "https://api.amazonalexa.com",
                                            "apiAccessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjEifQ.eyJhdWQiOiJodHRwczovL2FwaS5hbWF6b25hbGV4YS5jb20iLCJpc3MiOiJBbGV4YVNraWxsS2l0Iiwic3ViIjoiYW16bjEuYXNrLnNraWxsLmY0MDBjOWUzLTM3YzYtNDRlNS05ZTU4LTNjNWI5YmQyYzc0ZSIsImV4cCI6MTUxOTQxNjc5MCwiaWF0IjoxNTE5NDEzMTkwLCJuYmYiOjE1MTk0MTMxOTAsInByaXZhdGVDbGFpbXMiOnsiY29uc2VudFRva2VuIjpudWxsLCJkZXZpY2VJZCI6ImFtem4xLmFzay5kZXZpY2UuQUdVQVFUSEZETzZCSzdVQjQzVVFWUURISEQ0V1U0SDJBVUhLSzVTQ0JLNk9OV1FPRFNEWFY0QlFHVUw2RVZUUzI3WFRRUVVZQlNFRVpRNUo2U0RXWlJaNlBCWFJXMkRCV1JBUUg0V09HVUFFUkRQRDdPQUtJUTc2WUwyS1VBWVpGVExLSUJKVERWV0NTTkJRSVdQUklDUE42RTNRIiwidXNlcklkIjoiYW16bjEuYXNrLmFjY291bnQuQUhPUVJIVkVKVlpKTVBJT1hPV09EV0MzM0VKTFFaM0pMWUdCUllVQjY2WVgyNUZRWlE2NzdKSlpGTkpWRFZDMk9URUVQRDRKNEMzRk5LTzNURUtESUlZSDJMUEpISjJMTDZJTUJKVUZWNU9MUEVFQk5JM1c1SjNOM0lZNElaRVNMS1ZVTU5JSENGSjdZNkRHWVJYMjRFTERQRUs1TTRYTkxXVFpXSzVFS1UzU1VaNEc0WFFVSk9HR0FMMzNQMjI0SUJCVkNUTUJOWFJXSk1JIn19.S2AylqNx20aU5RUiMgOo8O9ClcNofg9RO062VdXLKFNrv29_jh4BDbMm39ykkHLcT-_a-HIJwaivtB-Hdm2dOgCisbArXF0_rXyqhMi6qUDh0HxgzDy58HbV8vR759MOV1a6eH_YpKVJfbe5Qdipi8fAS-NlXOnUVBKSyRdBK9QAKw1w8AOK6RQbGj427qj5M1LEe4sHXpl_l3LhC-E4NWfYBmSvwIsOYL0042V66yn-POrhnt6pwK5FkGR4fbS83X1NTcorkL_DFyAJPGZKwNhj4h_p3YDVD2GhuCYazZ_siVLyzfWsB2Rxe-2QYWPIEVjxhkFG83BLqPfSjh8BUw"
                                        }
                                    },
                                    "request": {
                                            "type": "SessionResumedRequest",
                                            "requestId": "amzn1.echo-api.request.2c940401-13f2-4224-82a7-c7b2276c1523",
                                            "timestamp": "2018-02-22T15:00:37Z",
                                            "locale": "en-US",
                                            "originIpAddress": "string",
                                            "cause": {
                                                "type": "ConnectionError",
                                                "token": "1234",
                                                "status": {
                                                    "code": "500",
                                                    "message": "INTERNAL ERROR"
                                                },
                                                "result": null
                                            }
                                    }
                                }"""
    const val DISPLAY_SELECTED_REQUEST = """{
    "version": "1.0",
    "session": {
        "new": false,
        "sessionId": "amzn1.echo-api.session.301facee-7b30-4533-a964-0510de81fe22",
        "application": {
            "applicationId": "amzn1.ask.skill.f400c9e3-37c6-44e5-9e58-3c5b9bd2c74e"
        },
        "user": {
            "userId": "amzn1.ask.account.AGIY5UNJO5YARVN2LC3SMFRSX36SQTRM53WGMQLCTIAPYZKGPGCFBGSNFAZELHTG54OGN5BFJ7UVG63PSKQS6PUVHRC6LWOICM6ILPUGJ2UWXJGSHRELWHBMWLUAC2WEM23WXZC7NP5XB4TKRUD2QNL3KMYA3S7NMDS7LNA7YUKUHE6U2HJRV5SDIJPG5WDESOK3ZW26ZRRFH7Y"
        }
    },
    "context": {
        "AudioPlayer": {
            "playerActivity": "IDLE"
        },
        "Display": {
            "token": "ListTemplate2ScreenToken"
        },
        "System": {
            "application": {
                "applicationId": "amzn1.ask.skill.f400c9e3-37c6-44e5-9e58-3c5b9bd2c74e"
            },
            "user": {
                "userId": "amzn1.ask.account.AGIY5UNJO5YARVN2LC3SMFRSX36SQTRM53WGMQLCTIAPYZKGPGCFBGSNFAZELHTG54OGN5BFJ7UVG63PSKQS6PUVHRC6LWOICM6ILPUGJ2UWXJGSHRELWHBMWLUAC2WEM23WXZC7NP5XB4TKRUD2QNL3KMYA3S7NMDS7LNA7YUKUHE6U2HJRV5SDIJPG5WDESOK3ZW26ZRRFH7Y"
            },
            "device": {
                "deviceId": "amzn1.ask.device.AGQEVCVC6VRUTM2BMO7URFVEMOIOXEV3JHKRP3QDIDQDTVYGQ5NZVHO76RWBGCQ3FJ6AL2D3JWGWSLHBH7UEBPCXHBHIO4QZJ3PEC4DQ7Z5MVFTQDIOTMGYBXKX5UIV2P25ADABPNO3UARKAC7AEAQ7ADLNQ",
                "supportedInterfaces": {
                    "AudioPlayer": {},
                    "Display": {
                        "templateVersion": "1.0",
                        "markupVersion": "1.0"
                    },
                    "VideoApp": {}
                }
            },
            "apiEndpoint": "https://api.amazonalexa.com",
            "apiAccessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjEifQ.eyJhdWQiOiJodHRwczovL2FwaS5hbWF6b25hbGV4YS5jb20iLCJpc3MiOiJBbGV4YVNraWxsS2l0Iiwic3ViIjoiYW16bjEuYXNrLnNraWxsLmY0MDBjOWUzLTM3YzYtNDRlNS05ZTU4LTNjNWI5YmQyYzc0ZSIsImV4cCI6MTUxOTc0MzI0NiwiaWF0IjoxNTE5NzM5NjQ2LCJuYmYiOjE1MTk3Mzk2NDYsInByaXZhdGVDbGFpbXMiOnsiY29uc2VudFRva2VuIjpudWxsLCJkZXZpY2VJZCI6ImFtem4xLmFzay5kZXZpY2UuQUdRRVZDVkM2VlJVVE0yQk1PN1VSRlZFTU9JT1hFVjNKSEtSUDNRRElEUURUVllHUTVOWlZITzc2UldCR0NRM0ZKNkFMMkQzSldHV1NMSEJIN1VFQlBDWEhCSElPNFFaSjNQRUM0RFE3WjVNVkZUUURJT1RNR1lCWEtYNVVJVjJQMjVBREFCUE5PM1VBUktBQzdBRUFRN0FETE5RIiwidXNlcklkIjoiYW16bjEuYXNrLmFjY291bnQuQUdJWTVVTkpPNVlBUlZOMkxDM1NNRlJTWDM2U1FUUk01M1dHTVFMQ1RJQVBZWktHUEdDRkJHU05GQVpFTEhURzU0T0dONUJGSjdVVkc2M1BTS1FTNlBVVkhSQzZMV09JQ002SUxQVUdKMlVXWEpHU0hSRUxXSEJNV0xVQUMyV0VNMjNXWFpDN05QNVhCNFRLUlVEMlFOTDNLTVlBM1M3Tk1EUzdMTkE3WVVLVUhFNlUySEpSVjVTRElKUEc1V0RFU09LM1pXMjZaUlJGSDdZIn19.LV6Mws2boRvkG1C0pcW2I7f3MDq5ZCn1aYyz5lY7reLehKGWGmFks9kb9PAPzLUB6LTeDBYUlwpNG5wdWbop7nu12ScRI6LWzj1Ekn-ffN4RVbNXsLhMQJ2W45r3MABa1ohN0ltb4capb29zT2tF9UJaBzqUK5o7QfazWOWFvOHm4w2jVbFSVNQEhP3JzvqW4z7ITRA5-aVSzEha3LwTejvleaXOtGGpDh90HxjbteHDtWdDDLAMVTBNYi4HRb7aU88gSyUkfqKTayPGIf4Pfo8U60czMgmMYXCNlwM6f0l1ZKeSgLbpo2_00ovVsx0sXzp_L9rPTk-Gn3tNovgmOA"
        }
    },
    "request": {
        "type": "Display.ElementSelected",
        "requestId": "amzn1.echo-api.request.72d85b22-0c2a-4b6b-88e0-3df22f7b1fec",
        "timestamp": "2018-02-27T13:54:06Z",
        "locale": "en-US",
        "token": "TeamsIntent\\|Internacional"
    }
}"""
    val LIST_ITEM_CREATED_EVENT_REQUEST = """{
    "version": "1.0",
    "context": {
        "System": {
            "application": {
                "applicationId": "amzn1.ask.skill.7b317edf-ec6b-4f01-916b-ecd1b980e9c1"
            },
            "user": {
                "userId": "amzn1.ask.account.AETBG4VL43EXDW5ZET6R3JCCUVL5WK23CPRNVOOU5LA25SMQPBJKAFPKLNCJYD6O72C5VXMHSYPVBGDE2QLJDATCAVZUDXQQ4BMRZKPNBXRGJYKJNZYBLM5DNYYMWCLVV4XNAEKRWWGDAKE5CFECBYSWEN2PHVDBN7KUAPPTPAED7MK467TW6MQKL5MTKHTZJY6XSNQ64JOT4HQ",
                "permissions": {
                    "consentToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjEifQ.eyJhdWQiOiJodHRwczovL2FwaS5hbWF6b25hbGV4YS5jb20iLCJpc3MiOiJBbGV4YVNraWxsS2l0Iiwic3ViIjoiYW16bjEuYXNrLnNraWxsLjdiMzE3ZWRmLWVjNmItNGYwMS05MTZiLWVjZDFiOTgwZTljMSIsImV4cCI6MTUyNzE3MTQwMiwiaWF0IjoxNTI3MTY3ODAyLCJuYmYiOjE1MjcxNjc4MDIsInByaXZhdGVDbGFpbXMiOnsiY29uc2VudFRva2VuIjoiQXR6YXxJd0VCSUtpM0E2XzFoNzBWWVQ2M2IyX053R3R0cE5iamU0SHh6c3prYlE2QVZHNTZCLVNKcG9BclRYWlh1R1dBTXFFQS1tdDhqZ3d5UkNPeVl3S3JHNFRodGpBR3F5dEpQREh5aWZWNkdjSVlDZ0I4enNvRHhoTzl1cnJ5WE5yWnExUGVtUkpJRFk5QU03WHVzb3dzMHJTQnU5LUIzMGJZUWY5dXFtVXZmcU1CUGxMd0tlaHAtOGhLc0NjckV1eGtxOUctc3RYWTdEamtIYWh4OS1FcFhrUkFtb3d0bE5MMkdDMVBqSjM2c21CMzBVTndlNkVrX1ZHOE9yMDM1YTk5eUV6dWJhRWxLVk1yREZ4cUVEYzNoUHFRZk9EelJhdnFrX0NiSlhGTTZNNGRKWGc5a0tqTjVwV3hyUjdRZ3dwYTBwOGJ1MmtTRUxVaVgtNTJ1T3NfYkotQy1oVGwzcGllLW1DLWs4M0V0dWUxd0s5dHA1aDNrNEZ5MW1CUGdpWDhoUlo0TS1YWnczVGh4ckxkT19IVFZ6cXRnSkt1LTJUX0tRbTVFT3BMaTRVSzRCWGI0Y0xHUHFvRTZuZ1Bvejg5WV9iZXlvQXZPVy0tLWlZV0RGaVBodzlDV0wwemlZX1NnXzlYeWlwa3N1NHNMTUVFLXdaa1dvQ0ROaFZPc18zc1g4eWZmLVB1NVIyd0lzLWZIWG82aEo1WkNrZlUiLCJ1c2VySWQiOiJhbXpuMS5hc2suYWNjb3VudC5BRVRCRzRWTDQzRVhEVzVaRVQ2UjNKQ0NVVkw1V0syM0NQUk5WT09VNUxBMjVTTVFQQkpLQUZQS0xOQ0pZRDZPNzJDNVZYTUhTWVBWQkdERTJRTEpEQVRDQVZaVURYUVE0Qk1SWktQTkJYUkdKWUtKTlpZQkxNNUROWVlNV0NMVlY0WE5BRUtSV1dHREFLRTVDRkVDQllTV0VOMlBIVkRCTjdLVUFQUFRQQUVEN01LNDY3VFc2TVFLTDVNVEtIVFpKWTZYU05RNjRKT1Q0SFEifX0.BrGj7p3JR9Nbp4OQXENottQPxURfVCIBlRqKocUpX08d9ZA98qQ0sklAi569unCueRoKKrgBV2BxgPvG1Sp-b8ERPDvKa-Ny788dL0RsQs4sCTZNn2NcUDZM_oFfB9zy_lpSHHZ_dhLQEndDMfPdh-4pbruspasPldtImUdQ2XkVxeyixS972Xz-0sFOwU38Z2dflOeGsgjYXOcCcqI2c1HGMugV-EOWt5YcSUJY5FHr75SS7NYoHEVUjhThVXTsOWc2YmA-0iuOuzZRHE-efssQ393GuHoUEywCYJSFcjQeJ0VPdf_IAvHpczt6ocnO5GJmn8IEq1BD50Iwwor1SA"
                }
            },
            "apiEndpoint": "https://api.amazonalexa.com",
            "apiAccessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjEifQ.eyJhdWQiOiJodHRwczovL2FwaS5hbWF6b25hbGV4YS5jb20iLCJpc3MiOiJBbGV4YVNraWxsS2l0Iiwic3ViIjoiYW16bjEuYXNrLnNraWxsLjdiMzE3ZWRmLWVjNmItNGYwMS05MTZiLWVjZDFiOTgwZTljMSIsImV4cCI6MTUyNzE3MTQwMiwiaWF0IjoxNTI3MTY3ODAyLCJuYmYiOjE1MjcxNjc4MDIsInByaXZhdGVDbGFpbXMiOnsiY29uc2VudFRva2VuIjoiQXR6YXxJd0VCSUtpM0E2XzFoNzBWWVQ2M2IyX053R3R0cE5iamU0SHh6c3prYlE2QVZHNTZCLVNKcG9BclRYWlh1R1dBTXFFQS1tdDhqZ3d5UkNPeVl3S3JHNFRodGpBR3F5dEpQREh5aWZWNkdjSVlDZ0I4enNvRHhoTzl1cnJ5WE5yWnExUGVtUkpJRFk5QU03WHVzb3dzMHJTQnU5LUIzMGJZUWY5dXFtVXZmcU1CUGxMd0tlaHAtOGhLc0NjckV1eGtxOUctc3RYWTdEamtIYWh4OS1FcFhrUkFtb3d0bE5MMkdDMVBqSjM2c21CMzBVTndlNkVrX1ZHOE9yMDM1YTk5eUV6dWJhRWxLVk1yREZ4cUVEYzNoUHFRZk9EelJhdnFrX0NiSlhGTTZNNGRKWGc5a0tqTjVwV3hyUjdRZ3dwYTBwOGJ1MmtTRUxVaVgtNTJ1T3NfYkotQy1oVGwzcGllLW1DLWs4M0V0dWUxd0s5dHA1aDNrNEZ5MW1CUGdpWDhoUlo0TS1YWnczVGh4ckxkT19IVFZ6cXRnSkt1LTJUX0tRbTVFT3BMaTRVSzRCWGI0Y0xHUHFvRTZuZ1Bvejg5WV9iZXlvQXZPVy0tLWlZV0RGaVBodzlDV0wwemlZX1NnXzlYeWlwa3N1NHNMTUVFLXdaa1dvQ0ROaFZPc18zc1g4eWZmLVB1NVIyd0lzLWZIWG82aEo1WkNrZlUiLCJ1c2VySWQiOiJhbXpuMS5hc2suYWNjb3VudC5BRVRCRzRWTDQzRVhEVzVaRVQ2UjNKQ0NVVkw1V0syM0NQUk5WT09VNUxBMjVTTVFQQkpLQUZQS0xOQ0pZRDZPNzJDNVZYTUhTWVBWQkdERTJRTEpEQVRDQVZaVURYUVE0Qk1SWktQTkJYUkdKWUtKTlpZQkxNNUROWVlNV0NMVlY0WE5BRUtSV1dHREFLRTVDRkVDQllTV0VOMlBIVkRCTjdLVUFQUFRQQUVEN01LNDY3VFc2TVFLTDVNVEtIVFpKWTZYU05RNjRKT1Q0SFEifX0.BrGj7p3JR9Nbp4OQXENottQPxURfVCIBlRqKocUpX08d9ZA98qQ0sklAi569unCueRoKKrgBV2BxgPvG1Sp-b8ERPDvKa-Ny788dL0RsQs4sCTZNn2NcUDZM_oFfB9zy_lpSHHZ_dhLQEndDMfPdh-4pbruspasPldtImUdQ2XkVxeyixS972Xz-0sFOwU38Z2dflOeGsgjYXOcCcqI2c1HGMugV-EOWt5YcSUJY5FHr75SS7NYoHEVUjhThVXTsOWc2YmA-0iuOuzZRHE-efssQ393GuHoUEywCYJSFcjQeJ0VPdf_IAvHpczt6ocnO5GJmn8IEq1BD50Iwwor1SA"
        }
    },
    "request": {
        "type": "AlexaHouseholdListEvent.ItemsDeleted",
        "requestId": "b795c1d2-2577-4afb-a4f6-0d995fd54a98",
        "timestamp": "2018-05-24T13:16:42Z",
        "eventCreationTime": "2018-05-24T13:16:42Z",
        "eventPublishingTime": "2018-05-24T13:16:42Z",
        "body": {
            "listId": "YW16bjEuYWNjb3VudC5BRkhOQkNYVzdITUlQRFVVQ0tOUURJUjRPWjZBLVNIT1BQSU5HX0lURU0=",
            "listItemIds": [
                "16d17103-a425-4cb0-a799-5690daae70b9"
            ]
        }
    }
}"""
    const val CAN_FULFILL_INTENT_REQUEST = """{
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
                "type": "CanFulfillIntentRequest",
                "requestId": "b795c1d2-2577-4afb-a4f6-0d995fd54a98",
                "intent": {
                    "name": "PlaySound",
                    "slots": {
                        "Sound": {
                            "name": "Sound",
                            "value": "crickets"
                        }
                    }
                },
                "locale": "en-US",
                "timestamp": "2018-02-22T15:00:37Z"
            }
        }"""
}
