# au-property-mgmt-rest

```
mvn clean package docker:build
```

```
docker run --name au-property-mgmt-rest -p 8080:8080 -d au-property-mgmt-rest:latest
```

```
docker network connect au-network au-property-mgmt-rest
```

## Environment variables and default values

Get all persons:
```
PROXY_URL_ALL_PERSONS=https://egov-demo-ss3.westeurope.cloudapp.azure.com/restapi/GOV/M-LAND/RE-REG?xRoadInstance=EGOV-EXAMPLE&memberClass=GOV&memberCode=M-HOMEAFFAIRS&subsystemCode=POP-REG&serviceCode=persons&serviceVersion=1&dateFrom=1900-01-01T00:00:00.440Z&dateTo=2018-10-30T23:59:59.440Z
```
Get person by ID (%s is replaced with ID):
```
PROXY_URL_PERSON=https://egov-demo-ss3.westeurope.cloudapp.azure.com/restapi/GOV/M-LAND/RE-REG/%s?xRoadInstance=EGOV-EXAMPLE&memberClass=GOV&memberCode=M-JUSTICE&subsystemCode=CREC-REG&serviceCode=persons&serviceVersion=1
```
Pay tax (first %d is replaced with payer ID and second %d is replaced with current timestamp):
```
PROXY_URL_PAY_TAX=https://egov-demo-ss3.westeurope.cloudapp.azure.com/restapi/GOV/M-LAND/RE-REG?xRoadInstance=EGOV-EXAMPLE&memberClass=GOV&memberCode=M-FINANCE&subsystemCode=BUDGET-MGMT&serviceCode=pay&serviceVersion=1&amount=20.0&currency=USD&payerData=%d&referenceNumber=1213129552&paymentTime=%d
```
