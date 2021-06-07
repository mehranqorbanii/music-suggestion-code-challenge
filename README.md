# music suggestion challenge

An Approach to solve requested outputs and searches based on semi YAML file input.

---

## Introduction

Music suggestion problem is a component to extract customer detail , albums and request to be process from semi YAML
file grouping them in different part and handle requests based on them. overall steps are:

- get input as file pars and group them using Parser service.
- create _**InfoDetailResult**_ from parsed data to be meaningful for rest of the services.
- get InfoDetailResult as input from _**ExtractorService**_.
- handle each request using corresponding _**requestHandler**_.

Following Item will be discussed in following sections:

- Libraries
- Data structures
- Design patterns
- Improvements

---

## Libraries

for implementing this algorithm every process is tried to be as simple as possible with the least used libraries :

* APACHE Collection utils
* jackson
* lombok ( to write less boilerplate :D )

## Data Structure

The data structure chosen in the algorithm to keep different data (ex. customers, albums, requests) is ArrayList and the
reasons are :

* size of input data in all 3 forms in question (customers, albums, request) are guaranteed to be 1≤n,m,q≤100 and due to
  this fact search operations will not be heavy and arrayList is a suitable choice.
* Get and add operation in ArrayList is O(1)

## Design Pattern

There are mainly two design pattern used in this approach: Singleton and Strategy pattern
_**EX**_ :

* _**Parser(and most of the classes)**_ follows Singleton design pattern
* _**RequestHandler and it's implementations**_ follows Singleton design pattern && strategy pattern.

Apart from these DPs, a Layer architecture approached has been followed in code organization.

## Improvement

although this is a complete working code and will do the job done, it still can have so many improvements
_**EX**_ :

* more test must be added to increase coverage and confidence on code (special on edge cased)
* namings can be more accurate specially on _**RequestHandler**_ implementation for now they are just named based on
  question inputs.
* validation on _**Parse**_ input file can be more specific.
  