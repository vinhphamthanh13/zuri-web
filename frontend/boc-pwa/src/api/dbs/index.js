/**
 * This module support one pool connection on express
 */
import { MongoClient } from 'mongodb';
import config from '../../config';

const { mongoConnection } = config.localDB;

let Databases; // make sure one instance databases for whole app;

async function connectDb(url) {
  try {
    const database = await MongoClient.connect(
      url,
      { useNewUrlParser: true },
    );
    return database.db();
  } catch (err) {
    console.error('Failed to make connection!');
    console.error(err);
  }

  return null;
}

export async function initDatabases() {
  if (Databases) {
    return Databases;
  }

  const acl = await connectDb(mongoConnection);
  Databases = {
    acl,
  };
  return Databases;
}

export async function getDatabases() {
  await initDatabases();
  return Databases;
}

export async function getDb() {
  await initDatabases();
  return Databases.acl;
}
