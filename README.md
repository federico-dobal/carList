# API for managing dealers and car listings
API to upload files with car details such as:

- code
- make
- model
- power
- year
- color
- price

The upload migth be by meand of a CSV file or a JSON.

It also provides a functionality to search listing by:
- make
- model
- year
- color

## How to build the project?
The project can be build by executing:
    
     mvn clean install 

## How to execute the tests?
Tests are ran by executing:
          
          mvn test
          
## How to start the server?
The starts by executing:
    
     mvn spring-boot:run     
     
It serves on http://localhost:8080/

### Endpoint the server provides:

####Dealers
It let the clients create a new dealer, retrtieve all the dealers details as well as get a single dealer details:

These are the endpoints:

- Create a new dealer
    - method: POST
    - path: http://localhost:8080/dealer
    
- Retrieve all the dealers
    - method: GET
    - path: http://localhost:8080/dealers 
    
- Retrieve a single dealer
    - method: GET
    - path: http://localhost:8080/dealer/{id}      


####Listings 
It let the user upload a list of listing by POSTor by uploading a CVS file.
It also supports to retrieve all the listing details and to search using different filters.
Finally, it also provides a functionality to retrieve the details associated with a single listing.

- Upload a list of listing by POST endpoint:
    - method: POST
    - path: http://localhost:8080/listings/vehicle_listings/{dealerId}
    - Example:
          
          curl --location --request POST 'http://localhost:8080/listings/vehicle_listings/1' \
          --header 'Content-Type: application/json' \
          --data-raw '[ {
               "code": "a", 
               "make": "renault", 
               "model": "megane", 
               "kW": 132,
               "year": 2014, 
               "color": "red", 
               "price": 13990 
          } ]'

- Upload a list of listing by CSV file upload:
    - method: POST
    - path: http://localhost:8080/listings/upload_csv/{dealerId}
    - Example:
          
          curl --location --request POST 'http://localhost:8080//listings/upload_csv/1?file' \
          --form 'file=@"/files/listing.csv"'
          
- Get the list of all listings:
    - method: GET
    - path: http://localhost:8080/listings/
    - Example:
          
          curl --location --request GET 'http://localhost:8080/listings/'          

- Get the details of a single listing:
    - method: GET
    - path: http://localhost:8080/listing/{listingId}
    - Example:
          
          curl --location --request GET 'http://localhost:8080/listing/3'          

- Search listings by fields:
    - method: GET
    - path: http://localhost:8080/listings/search?
    - Search fields:
        - make
        - model
        - year
        - color
    - Example:
          
          curl --location --request GET 'http://localhost:8080/listings/search?year=2014&color=red'          


       

  

