import express from 'express';
const { MongoClient, ServerApiVersion } = require('mongodb');
import bodyParser from 'body-parser';

// Create a new express app
const app = express();

// Cluster password (RestAPI)
var password = "miyAE2S9tfJM4Ze"

// Set up body parser to parse request bodies
app.use(bodyParser.json());

// Set up a connection to the MongoDB database
const uri = "mongodb+srv://RestAPI:<miyAE2S9tfJM4Ze>@restapi.w748b.mongodb.net/?retryWrites=true&w=majority";
const client = new MongoClient(uri, { useNewUrlParser: true, useUnifiedTopology: true, serverApi: ServerApiVersion.v1 });
client.connect(err => {
  const collection = client.db("codes").collection("QR");
  // perform actions on the collection object
  client.close();
});

  // Set up routes
  app.get('/QR', async (req, res) => {
    // Fetch data from the "items" collection and send it back to the client
    const collection = client.db('codes').collection('QR');
    const items = await collection.find({}).toArray();
    res.send(items);
  });

  app.post('/QR', async (req, res) => {
    // Add a new item to the "items" collection
    const collection = client.db('codes').collection('QR');
    const result = await collection.insertOne(req.body);
    res.send(result);
  });

  // Start the server
  app.listen(3000, () => {
    console.log('API listening on port 3000');
  });
