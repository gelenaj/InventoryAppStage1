# InventoryAppStage1

CRITERIA | MEETS SPECIFICATIONS
-- | --
Overall Layout | No UI is required for this project.Hint: At minimum, you will need a main activity that has methods to read data, a Contract Java class, and a DbHelper Java class.Note: Even though UI is not required for this Stage, we highly recommend that you test your insert/read methods with log calls. Often, students do not realize their code has SQL syntax errors until the app is run and the methods are called which results in the project not passing.


CRITERIA | MEETS SPECIFICATIONS
-- | --
Compile Time Errors | The code compiles without errors.
Table Definition | There exists a contract class that defines name of table and constants.Inside the contract class, there is an inner class for each table created.The contract contains at minimum constants for the Product Name, Price, Quantity, Supplier Name, and Supplier Phone Number.
Table Creation | There exists a subclass of SQLiteOpenHelper that overrides onCreate() and onUpgrade().
Data Insertion | There is a single insert method that adds:Product NamePriceQuantitySupplier NameSupplier Phone NumberIt is up to you to decide what datatype (e.g. INTEGER, STRING) each of these values should be; however, it is required that there are at least 2 different datatypes (e.g. INTEGER, STRING).
Data Reading | There is a single method that uses a Cursor from the database to perform a query on the table to retrieve at least one column of data. Also the method should close the Cursor after it's done reading from it.




![inventory_home_screen](https://user-images.githubusercontent.com/29842242/43476424-8d84f738-94c6-11e8-9989-8c5f12894785.png)
![inventory_add_item](https://user-images.githubusercontent.com/29842242/43476425-8d972b88-94c6-11e8-9a5c-9fb4e92f4d79.png)
![inventory_empty_view](https://user-images.githubusercontent.com/29842242/43476426-8daa7378-94c6-11e8-9a4a-840e13586f89.png)

