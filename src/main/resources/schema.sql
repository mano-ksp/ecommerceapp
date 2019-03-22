CREATE TABLE `Account` (
                         `ID` bigint(20) NOT NULL AUTO_INCREMENT,
                         `FirstName` varchar(100) default NULL,
                         `LastName` varchar(100) default NULL,
                         `Address` text,
                         `City` varchar(100) default NULL,
                         `State` varchar(100) default NULL,
                         `Country` varchar(100) default NULL,
                         `Phone` int(11) default NULL,
                         `Mobile` int(11) default NULL,
                         `Email` varchar(100) NOT NULL,
                         `Modified` timestamp NOT NULL,
                         `Created` datetime NOT NULL,
                         PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Inventory` (
                           `ID` bigint(20) NOT NULL AUTO_INCREMENT,
                           `Name` varchar(100) default NULL,
                           `QuantityPerUnit` int(11) default NULL,
                           `QuantityMetric` varchar(10) default NULL,
                           `PerUnitPrice` decimal(15,2) NOT NULL,
                           `Discount` int(11) default NULL,
                           `Available` tinyint(1) default NULL,
                           `AvailableUnits` int(11) default NULL,
                           `Version` bigint(20) NOT NULL default 1,
                           PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `OrderDetails` (
                              `ID` bigint(20) NOT NULL AUTO_INCREMENT,
                              `OrderID` binary(16) default NULL,
                              `ProductID` bigint(20) default NULL,
                              `Price` decimal(15,2) default NULL,
                              `Quantity` int(11) default NULL,
                              `Discount` int(11) default NULL,
                              `Created` datetime NOT NULL,
                              `Modified` timestamp NOT NULL,
                              PRIMARY KEY (`ID`),
                              FOREIGN KEY `OrderDetails_Orders_ID_fk` (`OrderID`) REFERENCES Orders(`ID`),
                              FOREIGN KEY `OrderDetails_Inventory_ID_fk` (`ProductID`) REFERENCES Inventory(`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Orders` (
                        `ID` binary(16) ,
                        `AccountID` bigint(20) NOT NULL,
                        `PaymentID` bigint(20) default NULL,
                        `OrderDate` timestamp NULL default NULL,
                        `TxStatus` tinyint(1) default NULL,
                        `ErrorMsg` text,
                        `Paid` tinyint(1) default NULL,
                        `PaidDate` timestamp NULL default NULL,
                        `ShipDate` timestamp NULL default NULL,
                        `Created` timestamp NULL default NULL,
                        `Modified` timestamp NULL default NULL,
                        PRIMARY KEY  (`ID`),
                        FOREIGN KEY `Orders_Account_ID_fk` (`AccountID`) REFERENCES Account(`ID`),
                        FOREIGN KEY `Orders_Payment_ID_fk` (`PaymentID`) REFERENCES Payment(`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Payment` (
                         `ID` bigint(20) NOT NULL AUTO_INCREMENT,
                         `OrderID` bigint(20) default NULL,
                         `Type` varchar(100) default NULL,
                         PRIMARY KEY  (`ID`),
                         FOREIGN KEY `Payment_Orders_ID_fk` (`OrderID`) REFERENCES Orders(`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `UserSecrets` (
                             `ID` bigint(2
                               0) default NULL AUTO_INCREMENT,
                             `AccountID` bigint(20) default NULL,
                             `UserName` varchar(100) default NULL,
                             `Password` binary(32) default NULL,
                             `CreditCard` varchar(100) default NULL,
                             `DebitCard` varchar(100) default NULL,
                             `Modified` timestamp NULL default NULL,
                             `Created` timestamp NULL default NULL,
                             PRIMARY KEY (`ID`),
                             FOREIGN KEY `UserSecrets_Account_ID_fk` (`AccountID`) REFERENCES Account(`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
