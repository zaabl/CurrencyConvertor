# Currency-Convertor
An application that converst currencies built on MVVM design pattern.

## Overview
The application is composed of a simple UI interface for allowing the user to convert currencies, where the system uses an API that calls it via Retrofit,
and uses coroutines and state flow for ensuring optimal performance, also the system's dependencies are based on the dependency injection principle which is implemented
by the usage of Dagger-Hilt.



![Alt Text](https://media.giphy.com/media/stlFB4h6zQHxVTiO4A/giphy.gif)

## System Architecture
The system starts by calling the ViewModel, requesting the currencies, which in order calls the repository which was injected by the AppModule, then the repository calls the Retrofit object which was also injected by the AppModule, then calls the API and gets the response sending it all the way back to the ViewModel, then the ViewModel receives the required currencies and the value, comparing the values and then returns the requested currency to the main activity. 


<p align="center">
  <img width="800" height="600" src="https://i.imgur.com/zB699gV.png">
</p>

## The API
The used API was originally created by Fawaz Ahmed, which has multiple endpoints, in our case, we did use the latest/currencies/eur.json which returns all the available currencies rate in euros.
https://github.com/fawazahmed0/currency-api


## Acknowledgments
• Author: **Hossam Zaabl**

https://github.com/zaabl

• API Owner: **Fawaz Ahmed**

https://github.com/fawazahmed0
