Application requests NBP api and provides endpoints to retrieve:
- average exchange rate for currency
  (http://localhost:8080/averageExchangeRate)
- max and min average rate for currency for provided number of quotations
  (http://localhost:8080/maxAndMinAverageRate)
- major difference between buy ask rate for given currency and number of quotation
  (http://localhost:8080/majorDifferenceBetweenBuyAskRate)

To run the server: 
- go to the main project folder (currency)
- open terminal and run: docker build -t pp/docker-currency:v1 .
- when docker image is done run: docker run -d -p 8080:8080 pp/docker-currency:v1

To test the app:
- go to: http://localhost:8080/swagger-ui/index.html
- chose endpoint to test -> Try it out

please keep under consideration:
- provided currency code should be standard ISO 4217
- date format should be formatted RRRR-MM-DD



