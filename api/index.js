const express = require('express');
const { MongoClient, ServerApiVersion } = require('mongodb');
const bodyParser = require('body-parser');
const { json } = require('express');
const cors = require('cors');
const authConfig = require('./auth.config');

// Create a new express app
const app = express();

// PORT
const PORT = 3000;

// Set up body parser to parse request bodies
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(cors({
     origin: '*',
     Header: 'Access-Control-Allow-Origin',
     methods: 'GET, POST, PUT, DELETE, OPTIONS',
  }));


// Set up a connection to the MongoDB database
const uri = "mongodb+srv://RestAPI:"+ authConfig.password +"@restapi.w748b.mongodb.net/?retryWrites=true&w=majority";
const client = new MongoClient(uri, { useNewUrlParser: true, useUnifiedTopology: true, serverApi: ServerApiVersion.v1 });
client.connect(err => {
  const collection = client.db("codes").collection("QR");
  // perform actions on the collection object
  // client.close();
  
});

  // Set up routes
  app.get('/items', async (req, res) => {
    // Fetch data from the "codes" collection and send it back to the client
    const collection = client.db('codes').collection('QR');
    const items = await collection.find({}).toArray();
    res.send(items);
  });

  app.get('/items/last5', async (req, res) => {
    // Fetch the last 5 entries from the "codes" collection and send it back to the client
    const collection = client.db('codes').collection('QR');
    const items = await collection.find().limit(5).sort({$natural:-1}).toArray(); 
    res.send(items);
  });

  app.get('/items/:id', async (req, res) => {
    // Fetch a single item from the "codes" collection and send it back to the client
    const collection = client.db('codes').collection('QR');
    const item = await collection.findOne({ _id: req.params.id });
    res.send(item);
  });

  app.post('/items', async (req, res) => {
    // Add a new item to the "codes" collection
    const collection = client.db('codes').collection('QR');
    const result = await collection.insertOne(req.body);
    res.send(result);
  });

  app.get('/items/ids/:id', async (req, res) => {
    // Fetch Data from barcodeItems and check if the id matches one of the ids inside the barcodeItems Object
    let data = handleBarcode({_id: req.params.id });
    console.log(data);
    res.send(data);
  });

  // Start the server
  app.listen(PORT, () => {
    console.log('API listening on port ' + PORT);
  });

  function handleBarcode(barcode) {
    console.log(barcode._id);
    const data = {
        Apples: "12345",
        Oranges: "67890",
        Bananas: "56435",
        Pears: "12345",
        Grapes: "67890",
        Strawberries: "56435"
    }
    switch (barcode._id) {
        case data.Apples:
            return "Apples";
        case data.Oranges:
            return "Oranges";
        case data.Bananas:
            return "Bananas";
        case data.Pears:
            return "Pears";
        case data.Grapes:
            return "Grapes";
        case data.Strawberries:
            return "Strawberries";
        default:
            return "Barcode not found.";
    }
}