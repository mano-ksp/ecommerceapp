###### **Requirements:**

java-8

maven

To Run App -- `mvn clean package jetty:run`

API
---

**_Initialize Data_**

To initialize DB - `POST localhost:9999/api/v1/db/init`
Header - `auth-token:superadminmano`

**_Inventory API_**

To get items in stock - `GET localhost:9999/api/v1/inventory?type=inStock`

To get items out of stock - `GET localhost:9999/api/v1/inventory?type=outOfStock`

To get all items in inventory - `GET localhost:9999/api/v1/inventory`

_**Sales API**_

To get total sales by customer = `GET localhost:9999/api/v1/totalsales?fromDate=2019-03-22&toDate=2019-03-23&classifier=byCustomer`

To get total sales by product = `GET localhost:9999/api/v1/totalsales?fromDate=2019-03-22&toDate=2019-03-23&classifier=byProduct`

To get total sales by date = `GET localhost:9999/api/v1/totalsales?fromDate=2019-03-22&toDate=2019-03-23&classifier=byDate`

_**Order API**_

To order items - `POST localhost:9999/api/v1/order`

Samples order data to send to API

-d `{"orders":[{"productName":"Milk","orderQuantity":2,"productId":1},{"productName":"Burger","orderQuantity":2,"productId":2},{"productName":"Cake","orderQuantity":3,"productId":3}],"customer":{"customerId":"1"},"paymentType":"VISA"}`

-d `{"orders":[{"productName":"Milk","orderQuantity":5,"productId":1},{"productName":"Burger","orderQuantity":1,"productId":2},{"productName":"Cake","orderQuantity":3,"productId":3},{"productName":"Drinks","orderQuantity":6,"productId":5}],"customer":{"customerId":"2"},"paymentType":"MASTERCARD"}`

-d `{"orders":[{"productName":"Milk","orderQuantity":3,"productId":1},{"productName":"Drinks","orderQuantity":4,"productId":5},{"productName":"Gasoline","orderQuantity":5,"productId":4}],"customer":{"customerId":"3"},"paymentType":"OFFLINE"}`

-----
Every register is a client where they can issue independent request to server to process the bill payment
-----
Discount Process = `OrderProcessService.processIndividualProductOrders()`

Discount added to individual customers irrespective of products

Discount added to individual products irrespective of customers


-----
To Run the whole process
Call Main.java --- main func
