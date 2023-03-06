const express = require('express');
const { MongoClient, ServerApiVersion } = require('mongodb');
const bodyParser = require('body-parser');
const { json } = require('express');
const cors = require('cors');
const authConfig = require('./auth.config');

// Create a new express app
const app = express();

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
    // Fetch data from the "codes" collection and send it back to the client
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

  // Start the server
  app.listen(3000, () => {
    console.log('API listening on port 3000');
  });
