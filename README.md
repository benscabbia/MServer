# MServer


MServer and MClient was developed for my Masters final project. M (which simply stands for Masters), is software that connects software components, AKA Middleware. 

The middleware software acts as a service that operates between a data-store and the end-user. The software takes the messages from the Server (MServer) and sends them to the client (MClient) for execution via a Dashboard. In the current version, the software is able to query structured, semi-structured and unstructured data in a relational (MYSQL), non-relational (MongoDB) database system or a text file via Spark. 

## Introduction to software - Brief Introduction

My middleware is aimed at small to medium sized organisations who have smaller budgets and limited resources, yet still need to manage and analyse Big Data. The technology used is all open-source and the middleware has been designed to function in an existing heterogeneous network, so as to cause the least amount of disruption and expense. It operates without the need for file reorganisation and is capable of running on commodity hardware, therefore not requiring specialized equipment. Also, the middleware has been designed to combat the complexity that surrounds the field of Big Data management and analysis by being simple to use. 

Middleware ‘is the software that connects software components’.The middleware will enable an organisation to manage and analyse their data by sending user-inputted queries to a remote machine, without the need to move or download the data. The machine will then execute the queries, and return the results. Assuming the data is distributed among a number of machines (locally or remotely), the program is able to send and execute computational queries on each of these machines. 

## Overview of the Program 

The research and insight demonstrated in the previous chapters (in my thesis) were instrumental in the technical decisions I made when designing my middleware. My research helped me to understand how the software needed to help small organisations tackle the most prominent factors that make the management and analysis of Big Data problematic. Namely: complexity, variety, volume and cost.  

## Software Architecture – A technical overview 

My software is a working prototype of a middleware application that can perform a basic analysis on a non-local data set via distributed computing. Namely it can search a text file, count the frequency of words in a text file and perform a simple user-specified filter query on a database located on a separate machine. It is envisaged that the software would be made open source, and that further versions of the software would incorporate additional functionality. The middleware software has been designed to easily integrate within existing infrastructure. There are two distinct parts to the middleware: MServer (i.e the server computer) and MClient (i.e. a remote machine that holds the data). They follow the typical Server-Client(s) architecture, and therefore their relationship is one-to-many (1..*); one server is capable of handling an arbitrary number of clients in a heterogeneous network. As it is heterogeneous, the server is not affected by the client’s operating systems, processor or the type of data that may be present. The two components interact through a RESTful service, which utilises HTTP as the transport layer. As the MClient can be connected via the internet, they are not physically confined to a geographical location3.   

As Figure 2 demonstrates, the application sits above the local OS, the local data and the processing engine. The prototype has been integrated with a MySQL, MongoDB and Spark processing engine, thus enabling it to interact with a database (relational or non-relational) and with data not in a database (via Spark).   

![Architecture](http://i.imgur.com/nQaMe1P.png)

##MServer Dashboard 
![Dashboard1](http://i.imgur.com/S5egTOm.png)
![Dashboard2](http://i.imgur.com/D2qdhEI.png)
