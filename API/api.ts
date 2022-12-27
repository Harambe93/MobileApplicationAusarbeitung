import * as express from 'express';
import * as mongodb from 'mongodb';

const app = express();
const port = 3000;

app.use(express.json());

const uri = "mongodb+srv://<username>:<password>@cluster0.mongodb.net/test?retryWrites=true&w=majority";
const client = new mongodb.MongoClient(uri);
client.connect((err) => {
  const collection = client.db("test").collection("qrCodes");
  
  app.post('/qrCode', (req, res) => {
    const qrCode = req.body.qrCode;
    collection.insertOne({ qrCode }, (err, result) => {
      res.send(result);
    });
  });
});

app.listen(port, () => {
  console.log(`API listening at http://localhost:${port}`);
});
