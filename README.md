# best-selling-selector-batch ( HEAT )

A spring batch for finding best selling products in the market.

# Basic HDL

### This is generally an application for Spring Batch, Reader, Processor and Writer.

## Things to consider: 
* where to get the data from. 
* what format should the input be in.
* processing and selection criteria 
* viewing them 


## Facts: 
1. Spring Batch gives you the concept of chunking where rather than read/process/write each record one at a time you could read in several items as a chunk.
2. Process them as a single transaction
3. Write them as a single transaction.

## Domain Model: 
* We will consume Rest Api services to get the products from as in Json format
* One to many relationship from URL to Products
> First thought to is : read in a single URL and build a collection of Product objects that are ready to be processed and written out as a single transaction.

## Design: 

### Reader

1. Retrieve the URLs from database
2. Get Products from URLs
3. Create a List<Product> objects for the Processor step

### Processor

1. Integration of data from multiple Readers if applicable?
2. Special processing rules or calculation of derived data?
3. Preparation of Product object for Writers

### Writer

1. One unique writer for persisting Products to database
2. Second unique writer for POST to REST API
3. Each chunk will be composed of Products in a single URL. Each chunk in process should be transacted so that in the event of an exception or failure, any persisted changes can be rolled back.
> Q:In the event of an exception, is it possible to define custom rollback behavior for the REST API?

### Constrains: 
TBD: we need to find out what constrains might face this system. 
1. formatting constrains. 
2. third party products pullings changes. 

### Risks: 
1.  Maintainability of the batch job since it will depend on External Rest API endpoints providers.
2. Clean ups process and historical data processing. 
