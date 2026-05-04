const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
require('dotenv').config();

const authRoutes = require('./routes/auth');

const app = express();

app.use(cors());
app.use(express.json());

// Routes
app.use('/auth', authRoutes);

// Health check
app.get('/health', (req, res) => {
    res.json({ status: 'UP', service: 'auth-service' });
});

// Connexion MongoDB
const MONGO_URI = process.env.MONGO_URI || 'mongodb://localhost:27017/authdb';

mongoose.connect(MONGO_URI)
    .then(() => {
        console.log('MongoDB connecte');
        const PORT = process.env.PORT || 3001;
        app.listen(PORT, () => {
            console.log(`Auth service demarre sur port ${PORT}`);
        });
    })
    .catch(err => {
        console.error('Erreur MongoDB:', err);
        process.exit(1);
    });