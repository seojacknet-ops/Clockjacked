# 🎨 ASSETS.md — ClockJacked Brand & Personal Touches

---

## App Icon / Favicon

### Concept
The ClockJacked icon is Jack's Bali photo with a translucent analog clock face overlaid — personal, recognizable, and unmistakably ClockJacked. The icon has already been generated and pre-processed for all Android densities.

### Pre-Generated Icon Assets

The icon is ready to drop in. All densities are pre-rendered in `app-icon/`:

```
app-icon/
├── ic_launcher_source.png              ← 1024x1024 source (for Play Store / reference)
├── mipmap-mdpi/
│   ├── ic_launcher.png                 ← 48x48
│   ├── ic_launcher_round.png           ← 48x48
│   └── ic_launcher_foreground.png      ← 108x108
├── mipmap-hdpi/
│   ├── ic_launcher.png                 ← 72x72
│   ├── ic_launcher_round.png           ← 72x72
│   └── ic_launcher_foreground.png      ← 162x162
├── mipmap-xhdpi/
│   ├── ic_launcher.png                 ← 96x96
│   ├── ic_launcher_round.png           ← 96x96
│   └── ic_launcher_foreground.png      ← 216x216
├── mipmap-xxhdpi/
│   ├── ic_launcher.png                 ← 144x144
│   ├── ic_launcher_round.png           ← 144x144
│   └── ic_launcher_foreground.png      ← 324x324
└── mipmap-xxxhdpi/
    ├── ic_launcher.png                 ← 192x192
    ├── ic_launcher_round.png           ← 192x192
    └── ic_launcher_foreground.png      ← 432x432
```

### Installation (Claude Code)
Copy each density folder's contents directly into the corresponding `app/src/main/res/mipmap-*/` folder, replacing any existing icons. No generation or resizing needed — it's all done.

```bash
# From project root:
cp app-icon/mipmap-mdpi/* app/src/main/res/mipmap-mdpi/
cp app-icon/mipmap-hdpi/* app/src/main/res/mipmap-hdpi/
cp app-icon/mipmap-xhdpi/* app/src/main/res/mipmap-xhdpi/
cp app-icon/mipmap-xxhdpi/* app/src/main/res/mipmap-xxhdpi/
cp app-icon/mipmap-xxxhdpi/* app/src/main/res/mipmap-xxxhdpi/
```

### FAL.AI Prompt (NOT NEEDED — icon is pre-generated, keeping for reference only)

```
Minimal app icon, flat design, a stylized bold clock face with lightning bolt crack through it, electric purple (#7C4DFF) and cyan (#00E5FF) on pure black background, geometric, sharp edges, no text, no gradients, icon design, centered composition, high contrast
```

**Negative prompt:**
```
realistic, 3d, text, letters, words, blurry, gradients, soft, rounded, photographic, human, face
```

**Settings:**
- Model: Flux Pro or SDXL
- Size: 1024x1024 (square)
- Steps: 30
- Guidance: 7.5

### Alternative Prompt (Cleaner/Simpler)

```
Minimalist app icon, bold letters "CJ" monogram intertwined, electric purple and cyan neon glow on matte black background, modern typography, flat design, no gradients, centered, high contrast, icon design
```

### Android Adaptive Icon Setup

Once the icon image is generated, it needs to be converted for Android:

```
res/
├── mipmap-mdpi/       (48x48)
├── mipmap-hdpi/       (72x72)
├── mipmap-xhdpi/      (96x96)
├── mipmap-xxhdpi/     (144x144)
├── mipmap-xxxhdpi/    (192x192)
└── mipmap-anydpi-v26/
    ├── ic_launcher.xml        (adaptive icon definition)
    └── ic_launcher_round.xml  (round variant)
```

**Adaptive Icon XML** (`res/mipmap-anydpi-v26/ic_launcher.xml`):
```xml
<?xml version="1.0" encoding="utf-8"?>
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <background android:drawable="@color/ic_launcher_background"/>
    <foreground android:drawable="@mipmap/ic_launcher_foreground"/>
</adaptive-icon>
```

**Background color** (`res/values/ic_launcher_background.xml`):
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="ic_launcher_background">#000000</color>
</resources>
```

**Foreground:** The generated icon image, cropped to safe zone (66% of full size — Android adaptive icons mask the outer 33%).

### Icon Generation Script (for Claude Code)

```bash
# After placing the generated icon as icon_source.png (1024x1024):
# Install ImageMagick if not available
# Generate all density variants for foreground

convert icon_source.png -resize 108x108 app/src/main/res/mipmap-mdpi/ic_launcher_foreground.png
convert icon_source.png -resize 162x162 app/src/main/res/mipmap-hdpi/ic_launcher_foreground.png
convert icon_source.png -resize 216x216 app/src/main/res/mipmap-xhdpi/ic_launcher_foreground.png
convert icon_source.png -resize 324x324 app/src/main/res/mipmap-xxhdpi/ic_launcher_foreground.png
convert icon_source.png -resize 432x432 app/src/main/res/mipmap-xxxhdpi/ic_launcher_foreground.png

# Also generate legacy icons (for pre-API 26)
convert icon_source.png -resize 48x48 app/src/main/res/mipmap-mdpi/ic_launcher.png
convert icon_source.png -resize 72x72 app/src/main/res/mipmap-hdpi/ic_launcher.png
convert icon_source.png -resize 96x96 app/src/main/res/mipmap-xhdpi/ic_launcher.png
convert icon_source.png -resize 144x144 app/src/main/res/mipmap-xxhdpi/ic_launcher.png
convert icon_source.png -resize 192x192 app/src/main/res/mipmap-xxxhdpi/ic_launcher.png
```

### Widget Icon
Reuse the app icon at smaller sizes for the widget picker preview. No separate design needed.

---

## Jack's Profile Photo — About Screen

### Placement
Jack's photo appears on the About screen as a circular avatar, centered above the crew credits.

### Image Setup

**Two pre-processed versions are included in the repo:**
- `jack_profile.jpg` — Full square crop (512x512, 59KB) — more background visible
- `jack_profile_tight.jpg` — Tight face crop (512x512, 52KB) — **RECOMMENDED for circular avatar**

**Use the tight crop.** It centers the face perfectly when masked to a circle.

1. Place the chosen photo in:
   ```
   app/src/main/res/drawable/jack_profile.jpg
   ```

2. Source photo: Jack in Bali, golden hour, black cap, grey Boss tee, Rip Curl surf mural behind. Already compressed and optimized — no further processing needed.

### Composable Implementation

```kotlin
@Composable
fun AboutScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // App icon or logo
        Text(
            text = "ClockJacked ⚡",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Jack's profile photo — circular with accent border
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape)
        ) {
            Image(
                painter = painterResource(id = R.drawable.jack_profile),
                contentDescription = "Jack — the creator",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Crew credits
        Text(
            text = "Built with love by",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Crew names — tappable for easter eggs
        CrewNameRow(
            names = listOf(
                CrewMemberInfo("Jack", "The wanderer 🌍"),
                CrewMemberInfo("Jake", "The rock 🏔️"),
                CrewMemberInfo("Campbell", "The legend 👑")
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Three mates. Multiple time zones.\nZero patience for ads.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "\"Wherever we are in the world — Utah, Hawaii,\nLiverpool, or Bali — we're always on\neach other's time.\"",
            style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Divider
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 48.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "No ads. No tracking. No BS.\nJust time.",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "v1.0.0 — Made in 24 hours ⏱️",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
        )
    }
}

// Tappable crew names with easter egg subtitles
data class CrewMemberInfo(val name: String, val subtitle: String)

@Composable
fun CrewNameRow(names: List<CrewMemberInfo>) {
    var revealedIndex by remember { mutableIntStateOf(-1) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        names.forEachIndexed { index, member ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable {
                    revealedIndex = if (revealedIndex == index) -1 else index
                }
            ) {
                Text(
                    text = member.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                AnimatedVisibility(visible = revealedIndex == index) {
                    Text(
                        text = member.subtitle,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            if (index < names.lastIndex) {
                Text(
                    text = "×",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                )
            }
        }
    }
}
```

### Photo Processing Notes
- If the original photo is large, Claude Code should resize to 512x512 and compress
- Use JPEG for photos (smaller file size), PNG for graphics
- Ensure the photo looks good when circular-cropped (face centered, not cut off)
- The accent border (3dp, primary color) adds a premium touch
- Photo adds ~50-200KB to APK — well within our 10MB budget

---

## Color Identity Summary

```
Brand Colors:
├── Primary Accent:    #7C4DFF (Electric Purple)
├── Secondary Accent:  #00E5FF (Cyan)
├── Background:        #000000 (AMOLED Black)
├── Surface:           #1A1A1A
├── Card:              #242424
├── Text Primary:      #FFFFFF
├── Text Secondary:    #B0B0B0

Status Colors:
├── Call Green:        #4CAF50
├── Call Amber:        #FF9800
├── Call Red:          #F44336

Time Tints (7% opacity):
├── Night:             #1A237E
├── Dawn:              #E91E63
├── Morning:           #FFC107
├── Midday:            #FFD54F
├── Afternoon:         #FF9800
├── Sunset:            #FF5722
└── Evening:           #7B1FA2
```

---

## File Checklist for Assets

```
app/src/main/res/
├── drawable/
│   └── jack_profile.jpg          ← Jack's photo (512x512, circular crop ready)
├── mipmap-mdpi/
│   ├── ic_launcher.png           ← 48x48
│   └── ic_launcher_foreground.png ← 108x108
├── mipmap-hdpi/
│   ├── ic_launcher.png           ← 72x72
│   └── ic_launcher_foreground.png ← 162x162
├── mipmap-xhdpi/
│   ├── ic_launcher.png           ← 96x96
│   └── ic_launcher_foreground.png ← 216x216
├── mipmap-xxhdpi/
│   ├── ic_launcher.png           ← 144x144
│   └── ic_launcher_foreground.png ← 324x324
├── mipmap-xxxhdpi/
│   ├── ic_launcher.png           ← 192x192
│   └── ic_launcher_foreground.png ← 432x432
├── mipmap-anydpi-v26/
│   ├── ic_launcher.xml           ← Adaptive icon definition
│   └── ic_launcher_round.xml     ← Round variant
└── values/
    └── ic_launcher_background.xml ← #000000
```

---

*The icon breaks the mold. The photo makes it personal. The colors tie it all together.* ⚡
