# Biser Conveyor Client

## Usage example on success


```java

        BiserConveyorClient biserConveyorClient = BiserConveyorClient.newInstance();

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("phone", "+23424234234234");

        String send = biserConveyorClient.send(BCRequest.Builder.
                create().
                addType(BCRequest.Type.SEARCH).                     //Request type
                addCompany(23123).                                  //Debt company id
                addObject("1304820SD8S7SDF08AFSF987F7S896DFSDF8").  //Serialized debt pack
                addParameters(parameters).                          //Debt search parameters
                addSession("session").                              //API2.X session
                addPoint("12").                                     //Debt point type
                build());
```

## Usage example on exception

```java

        BiserConveyorClient biserConveyorClient = BiserConveyorClient.newInstance();

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("phone", "+23424234234234");

        String send = biserConveyorClient.send(BCRequest.Builder.
                create().
                addType(BCRequest.Type.SEARCH).                     //Request type
                addCompany(23123).                                  //Debt company id
                addCode("DT_000003").                               //Debt error code
                addParameters(parameters).                          //Debt search parameters
                addSession("session").                              //API2.X session
                addPoint("18").                                     //Debt point type
                build());
```

# Biser Conveyor Sever

## Call search debt method of B.I.S.E.R bridge

### HTTP Headers:

| Name  | Value | Optional |
| ------------- | ------------- | ------------- |
| Content-Type  | application/json | No |
| Accept  | application/json | No |
| debug  | NtcbnhjdfybtRjydtqthfDL',fuHt;bvt  | Yes |

### HTTP Request: 

Method: **POST**

Address: https://biser-conveyor.nc.pb.ua:8081/rest/call/search

Body:

```json
{
    "biserRequest" : {
        "parameters" : {
            "ACCOUNT" : "123109123"
        },
        "session" : "asdpa98d98a7da9s",
        "companyId" : 891272,
        "point" : 13,
        "type": "SEARCH"
    },
    "sys" : {
        "ref" : "sd23e2dpia9s8d09a8das",
        "obj_id" : null,
        "conv_id" : "18111",
        "node_id" : "28281"
    }
}
```

### HTTP Response:

```json
{
    "biserResponse" : {
        "message" : "Success response",
        "code" : "DT_OK",
        "body" : "H4sIAAAAAAAAAJVVXWgcVRS...SAD3==",
        "duration" : "2200",
        "type" : null
    }
}
```

## Print debt and api2x package difference

### HTTP Headers:

| Name  | Value | Optional |
| ------------- | ------------- | ------------- |
| Content-Type  | application/json | No |
| Accept  | application/json | No |

### HTTP Request: 

Method: **POST**

Address: https://biser-conveyor.nc.pb.ua:8081/rest/diff

Body:

```json
{
    "biserRequest" : {
        "biserPack" : "H4sIAAAAAAAAAJVVXWgcVRS...SAD3==",
        "api2xPack" : "H4sIAAAAAAAAAJVVXWgcVRS...SAD4==",
        "session" : "ae2s2s2x23s12cac"
    },
    "sys" : {
        "ref" : "sd23e2dpia9s8d09a8das",
        "obj_id" : null,
        "conv_id" : "18111",
        "node_id" : "28281"
    }
}
```

### HTTP Response:

```json
{
    "diff" : "OK"
}
```

# Company statistic service

## Adding request attributes for statistic calculation

### HTTP Request

### HTTP Headers:

| Name  | Value | Optional |
| ------------- | ------------- | ------------- |
| Content-Type  | application/json | No |
| Accept  | application/json | No |

Method: **POST**

Address: https://biser-conveyor.nc.pb.ua:8081/rest/stats/attributes

Body:

```json
{
        "company_id": "123456",
        "session": "abcdefg123237782378423",
        "point": 11,
        "type": "0",
        "status": "1",
        "diff": {
            "biserPack": "biser_pack",
            "api2xPack": "api2x_pack",
            "biserCode": "BS_01_01",
            "api2xCode": "BPLN_01_02"
        },
        "parameters" : {
            "param1" : "value1",
            "param2" : "value2"
        }
}
```

### HTTP Response

Body:

```json
{
        "company_id": "123456",
        "session": "abcdefg123237782378423",
        "point": 11,
        "type": "0",
        "status": "1",
        "diff": {
            "biserPack": "biser_pack",
            "api2xPack": "api2x_pack",
            "biserCode": "BS_01_01",
            "api2xCode": "BPLN_01_02"
        },
        "parameters" : {
            "param1" : "value1",
            "param2" : "value2"
        }
}
```

## Retrieving statistic for specific company

### HTTP Request

### HTTP Headers:

| Name  | Value | Optional |
| ------------- | ------------- | ------------- |
| Accept  | application/json | No |

Method: **GET**

Address: https://biser-conveyor.nc.pb.ua:8081/rest/stats?company_id={company_id}

### HTTP Response

### HTTP Headers:

| Name  | Value | Optional |
| ------------- | ------------- | ------------- |
| Content-Type  | application/json | No |

Body:

```json
{
        "companyId": "123456",
        "active": true
}
```

## Retrieving statistics for all companies

### HTTP Request

### HTTP Headers:

| Name  | Value | Optional |
| ------------- | ------------- | ------------- |
| Accept  | application/json | No |

Method: **GET**

Address: https://biser-conveyor.nc.pb.ua:8081/rest/stats/all?showActive=true

### HTTP Response

### HTTP Headers:

| Name  | Value | Optional |
| ------------- | ------------- | ------------- |
| Content-Type  | application/json | No |

### Request parameters:
| Name  | Value | Optional | Description
| ------------- | ------------- | ------------- | ------------- |
| showActive  | true/false | Yes | Show only companies that are active or not active

Body:

```json
{
        "companyStats": [
        {
            "companyId": "123456",
            "active": true
        },
        {
            "companyId": "123457",
            "active": true
        }
        ]
}
```
