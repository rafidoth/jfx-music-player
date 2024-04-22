const express = require('express');
const fs = require('fs');
const path = require('path');

const app = express();
const audioDirectory = path.join(__dirname, 'audio');
app.get('/audio/:musicId', (req, res) => {
    const musicId = req.params.musicId;
    console.log(musicId);
    const audioFilePath = path.join(audioDirectory, `${musicId}.mp3`);
    console.log(audioFilePath);
    if (!fs.existsSync(audioFilePath)) {
        return res.status(404).send('Audio file not found');
    }


    const stat = fs.statSync(audioFilePath);
    const fileSize = stat.size;

    const head = {
        'Content-Length': fileSize,
        'Content-Type': 'audio/mpeg',
    };
    res.writeHead(200, head);
    fs.createReadStream(audioFilePath).pipe(res);
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});
