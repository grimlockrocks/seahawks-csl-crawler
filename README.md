# seahawks-csl-crawler
Crawl the Seahawks Charter Seat License (CSL) marketplace and send a text message when somebody offers an affordable price

Steps:
* Create a new AWS Lambda function with Java 8, set trigger to be CloudWatch Event that invokes the Lambda function every hour. 
* In DataHandler.java, set the preferred sections, max price, and number of seats. 
* In SeahawksCSLCrawler.java, set the phone number. 
