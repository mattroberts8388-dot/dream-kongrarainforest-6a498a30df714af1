# KONGRA Rainforest

A Minecraft Fabric mod (1.20.1) that turns jungle biomes into a dangerous rainforest ecosystem:

- **Rain kills.** Stay out in the open rain too long (about 15 seconds) and you begin to lose health — over and over — until you get under cover or dry off.
- **Rainforest wildlife.** Jaguars, toucans, and rainforest beetles now spawn in jungles.
- **The Big Boss: KONGRA.** A towering beast with the body of a gorilla and the head of a King Cobra. It has 200 HP, massive armor, and hits like a truck. Without full KONGRA armor you deal only a quarter of your damage to it and take crushing extra hits — you don't stand a chance.
- **KONGRA Armor.** Camouflage-patterned armor (black, brown, neon green) crafted from the rarest rainforest materials: **KONGRA Scales** (dropped by KONGRA) and **Rainforest Gems** (mined from Rainforest Gem Ore). A full set gives strong protection AND completely stops rain damage.

## How to build the mod (no Java install needed — GitHub builds it for FREE)

You do **not** need to install Java or any developer tools. GitHub will compile the mod into a ready-to-use `.jar` file for you, for free.

Follow these steps exactly:

1. **Create a GitHub account** at https://github.com (free).
2. Click the **+** in the top right → **New repository**. Give it any name (e.g. `kongra-rainforest`) and click **Create repository**.
3. On the new empty repository page, click the link **"uploading an existing file"**.
4. **Extract the ZIP** you downloaded (double-click it) so you have a normal folder of files.
5. **macOS users — IMPORTANT:** this project contains a hidden folder named `.github` that GitHub NEEDS to run the build. In Finder it is **invisible by default**. Open the extracted folder in Finder and press **Cmd + Shift + .** (period) to reveal hidden files. You should now see the `.github` folder. If you skip this step the build will never run.
6. Open the extracted folder, **select ALL the files and folders INSIDE it** (including the hidden `.github` folder, `src`, `build.gradle`, `gradle.properties`, `settings.gradle`, `README.md`, etc.). Drag the **contents** into the GitHub upload page — **do NOT drag the outer folder itself**, drag what's inside it.
7. Scroll down and click **Commit changes**.
8. Click the **Actions** tab at the top of your repository. You'll see a build running.
9. Wait about **2 minutes** for it to finish (green check mark).
10. Click the finished build run, scroll down to **Artifacts**, and download **mod-jar**.
11. Unzip that download to get the `.jar` file.
12. Copy the `.jar` into your `.minecraft/mods/` folder (create the `mods` folder if it doesn't exist). You must have **Fabric Loader** and the **Fabric API** installed for Minecraft 1.20.1.
13. Launch Minecraft with the Fabric profile and enjoy the rainforest!

## Tips
- Find a jungle biome to encounter the wildlife and KONGRA.
- Mine **Rainforest Gem Ore** (looks like stone with neon-green speckles) with an iron pickaxe or better.
- Kill KONGRA to collect **KONGRA Scales**, then craft a full armor set to survive the rain and finish the fight.

## License
MIT