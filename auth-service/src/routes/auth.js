const express = require('express');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const User = require('../models/User');

const router = express.Router();

// POST /auth/register
router.post('/register', async (req, res) => {
    try {
        const { username, password } = req.body;
        if (!username || !password) {
            return res.status(400).json({ message: 'Username et password requis' });
        }
        const existing = await User.findOne({ username });
        if (existing) {
            return res.status(409).json({ message: 'Username deja pris' });
        }
        const hashed = await bcrypt.hash(password, 10);
        const user = await User.create({ username, password: hashed });
        res.status(201).json({ id: user._id, username: user.username });
    } catch (err) {
        res.status(500).json({ message: 'Erreur serveur', error: err.message });
    }
});

// POST /auth/login
router.post('/login', async (req, res) => {
    try {
        const { username, password } = req.body;
        const user = await User.findOne({ username });
        if (!user || !(await bcrypt.compare(password, user.password))) {
            return res.status(401).json({ message: 'Identifiants invalides' });
        }
        const token = jwt.sign(
            { userId: user._id, username: user.username },
            process.env.JWT_SECRET || 'secret_jwt_dev',
            { expiresIn: '1h' }
        );
        res.json({ token, username: user.username });
    } catch (err) {
        res.status(500).json({ message: 'Erreur serveur', error: err.message });
    }
});

// GET /auth/verify (vérifier un token)
router.get('/verify', (req, res) => {
    const authHeader = req.headers.authorization;
    if (!authHeader) return res.status(401).json({ message: 'Token manquant' });

    const token = authHeader.split(' ')[1];
    try {
        const decoded = jwt.verify(token, process.env.JWT_SECRET || 'secret_jwt_dev');
        res.json({ valid: true, user: decoded });
    } catch {
        res.status(401).json({ valid: false, message: 'Token invalide' });
    }
});

module.exports = router;