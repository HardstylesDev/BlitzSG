![version](https://img.shields.io/badge/version-1.0.0-blue)
<br><a href="https://github.com/badges/HardstylesDev/BlitzSG/contributors" alt="Contributors"><img src="https://img.shields.io/github/contributors/HardstylesDev/BlitzSG" /></a>

# Blitz Survival Games | 1.0.0

## Prior version of Hypixel's Blitz SG game mode

~~(and frankly, the superior version)~~

## Originally made as a fun project to further my knowledge of Java and Minecraft server development

Everything is functional, and the game is playable. However, there are a handful of missing details that I haven't
gotten around to adding yet.
These currently include:

- [ ] Deathmatch Arena's not shrinking
- [ ] Lobby gadgets (Teleport stick, etc) Might not add these.
- [x] Lobby cosmetics (Just added Aura's in the lobby for now?)
- [x] The Wardrobe

## Side note

This project was never intended to be used on a public server, and as such, most things are hard-coded. I have no plans
to change this.
<br>
<br>I did my best to create faithful remake of original game mode, through watching numerous old videos, recounting my
own
<br>memories of the game, receiving feedback from some extremely helpful people such as Oculysm and Panimioul and
discussing the game with other OG players.
<br>That being said, there are some things that I was unable to recreate, or simply forgot about. If you notice anything
<br>that is missing, or incorrect, please let me know!
<br>Due to me not having access to the original source code, some speculation was required, and as such, some game logic
may not be a 100% match to the original game mode.

## Features

- [x] All original kits (If you notice anything missing, please make a pull request)
- [x] All original maps (Including all but 1; Rainforest)
- [x] Chest loot that was always the same (I'm not sure if this was intentional or not, but I did it anyway. i.e. the
  iron chestplate chest on winter)
- [x] The original lobby
- [x] The original deathmatch arena
- [x] The original Blitz Stars
- [x] Perks based on ranks (VIP, VIP+, MVP, MVP+), this includes the kits and coin multipliers.
- [x] Spectating (I removed the VIP and above requirement for spectating, but I do still put you in the waiting lobby)
- [x] Original prices for kits and perks

## Technical Details

I haven't messed with bungee for this project, so it's all just a single server. I'm not sure if it's worth doing.
<br>I've done my due diligence to include 2 types of database support, MySQL and MongoDB. I personally use MySQL,
because I can't be bothered to set up a MongoDB server.

## Community Notes

While I enjoy working on this project I'm aware it's not going to get much use, at least on my end. I'm likely not going
to be hosting this publicly.
<br>
I really had a lot of fun working on this project, and I hope you enjoy it as much as I do.
<br>
This game has been a big part of my childhood, and I'm glad that at least now I'll have the opportunity to look back on
it and remember the good times I had playing it.
<br>

#### Some things I concluded while working on this project:

- No matter how 'perfect' this project could become, it will never be the same as the original game mode.
- The community has changed a lot, and with that, the game has changed a lot. The current version of Blitz is sadly
  ridden with hackers, and the community is not what it used to be.
- The playstyle has (obviously) changed over the years, and even if I were to host this publicly, I don't think it would
  have anywhere near the same feel as the original game mode.
  <br>

## Running

If you want to run this project, you'll need to set up a MySQL or MongoDB server, and configure the database settings in
the database.txt inside the BlitzSG plugin folder.
There's 2 zip files included in the root folder, named `arenas.zip` and `map-configs.zip`. These contain all the maps
and configs for the maps. You'll need to extract the arena's into the root folder of your server, and the folder
inside `arena-configs` into the BlitzSG plugin folder.
Fill in the database details in the database.txt file, and you should be good to go. Runs on any 1.8.8 spigot (or fork).

### Hierarchy

    .
    ├── root                   
    │   ├── ☕spigot.jar              # The server jar     
    │   └── 📁arenas      
    │       ├ 📁caelum                # Example map
    │       └ 📁...                   # More maps (all included in the zip)
    │   └── 📁worlds      
    │       └ 📁world                 # This is the hub world     
    │   └── 📁plugins      
    │       ├ BlitzSG.jar             # Hey it's this project    
    │       └ 📁BlitzSG               # Plugin folder
    │           ├ 📄database.txt      # Database config
    │           └ 📁arenas            # Arena configs
    │               ├ 📁caelum.yml    # Example arena config
    │               └ 📁...           # More arena configs (all included in the zip)
    └── ...

## Building
This project uses maven, so building is as simple as running `mvn install` & `mvn package` in the root directory.
<br>
The output jar will be located in the `target` folder.
<br>
It's using the shade plugin, so all dependencies will be included in the output jar.

## Contributing

Pull requests are always welcome. For all of you out there who aren't familiar with GitHub, you can also just message me
on discord (arraylist) and I'll add you as a contributor. I'm always happy to have more people contribute to this project.

## Support

If you need any help with this project, feel free to message me on discord (arraylist) or use any of my other contact information on my profile.
I'm always happy to help, especially if you share my passion for this game mode.


## Special Thanks

- **Oculysm**: For helping me with some of the details of the game mode, such as economy and kits
- **Panimioul**: For re-creating the original lobby, and helping me find some of the original maps through the archive
  discord
- **ILost**: For providing me with free hosting on numerous occasions, and helping me test the game
- **Pixelville**: for the hours spent testing the game (i'd include your friends but i don't know their usernames)
- **Hypixel Map Archive discord**: For having a detailed list of all the maps
- **kunet**: For creating the worldclean tool, which I used to clean up each map (they fit on GitHub now!)
- **The original Hypixel team**: For creating the original game mode, and giving us all the memories we have today
- **The OG community**: For being a part of the game mode, and making it what it was back in the day. I'm glad I was
  able to
  be a part of it. Miss you guys.
- **You**: For reading this far, and for being a part of the community. I hope you've enjoyed the ride as much as I did.
- **Anyone else I forgot to mention**: I'm sorry, I'm bad at remembering things. I'll add you if you remind me.
