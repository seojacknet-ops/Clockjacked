---
name: frontend-design
description: Create distinctive, production-grade frontend interfaces. Use for web components, pages, dashboards, artifacts, posters, or any UI work. Generates memorable, polished code that transcends generic AI aesthetics.
license: Complete terms in LICENSE.txt
---

# Frontend Design Skill

Create production-grade interfaces with genuine design craft. Every output should feel intentionally designed, not AI-generated.

## Pre-Implementation Protocol

**1. Context Analysis** (5 seconds)
- Who uses this? What emotion should they feel?
- What's the ONE memorable element?

**2. Aesthetic Commitment** — Pick ONE direction and commit fully:

| Direction | Characteristics | Typography | Color Strategy |
|-----------|-----------------|------------|----------------|
| **Brutalist** | Raw, exposed, confrontational | Mono, condensed sans | High contrast, limited palette |
| **Editorial** | Magazine-quality, typographic hierarchy | Serif headlines, refined body | Muted with strategic accent |
| **Retro-Future** | Nostalgic tech, CRT vibes | Pixel, display fonts | Neon on dark, scanlines |
| **Organic** | Natural, flowing, handcrafted | Rounded, humanist | Earth tones, gradual gradients |
| **Luxury** | Refined, spacious, precious | Elegant serif, tight tracking | Black/white + gold/accent |
| **Playful** | Bouncy, colorful, energetic | Rounded bold, variable weight | Saturated, unexpected combos |
| **Industrial** | Utilitarian, functional | Condensed sans, all-caps | Grays, caution yellows |
| **Glassmorphic** | Depth, transparency, layered | Clean geometric sans | Frosted overlays, vibrant blurs |
| **Neo-Geo** | Sharp shapes, bold geometry | Geometric sans, strong weight | Primary colors, sharp contrast |

**3. Complexity Calibration**
- Maximalist vision → elaborate implementation (animations, layers, effects)
- Minimalist vision → surgical precision (spacing, typography, subtle details)

---

## Implementation Requirements

### Typography (Never Generic)

```css
/* BANNED: Inter, Roboto, Arial, system-ui, sans-serif defaults */

/* APPROVED APPROACHES: */
/* Display: Clash Display, Cabinet Grotesk, Satoshi, Outfit, Syne, Unbounded */
/* Body: DM Sans, Plus Jakarta Sans, Manrope, General Sans */
/* Editorial: Playfair Display, Cormorant, Libre Baskerville */
/* Mono: JetBrains Mono, IBM Plex Mono, Fira Code */
/* Statement: Bebas Neue, Oswald, Anton, Big Shoulders */

@import url('https://fonts.googleapis.com/css2?family=...');

:root {
  --font-display: 'Clash Display', sans-serif;
  --font-body: 'DM Sans', sans-serif;
}
```

### Color Architecture

```css
/* Define a deliberate system, not random values */
:root {
  /* Core palette - 2-3 dominant colors max */
  --color-surface: #0a0a0f;
  --color-surface-elevated: #141419;
  --color-text: #fafafa;
  --color-text-muted: #71717a;
  
  /* Accent - ONE strong choice */
  --color-accent: #3b82f6;
  --color-accent-hover: #60a5fa;
  
  /* Semantic */
  --radius-sm: 4px;
  --radius-md: 8px;
  --radius-lg: 16px;
  --shadow-elevated: 0 8px 32px rgba(0,0,0,0.4);
}
```

### Motion Hierarchy

**Priority Order:**
1. **Page Load** — Staggered entrance (most impact)
2. **Scroll Reveals** — Intersection Observer triggers
3. **Hover States** — Micro-feedback
4. **Continuous** — Ambient effects (use sparingly)

```css
/* CSS-first animation pattern */
@keyframes fadeSlideIn {
  from { opacity: 0; transform: translateY(20px); }
  to { opacity: 1; transform: translateY(0); }
}

.stagger-item {
  animation: fadeSlideIn 0.6s ease-out forwards;
  opacity: 0;
}
.stagger-item:nth-child(1) { animation-delay: 0ms; }
.stagger-item:nth-child(2) { animation-delay: 80ms; }
.stagger-item:nth-child(3) { animation-delay: 160ms; }
/* Continue pattern... */

/* Hover with intention */
.interactive {
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}
.interactive:hover {
  transform: translateY(-2px);
  box-shadow: var(--shadow-elevated);
}
```

**React Motion (when available):**
```jsx
import { motion } from 'framer-motion';

const container = {
  hidden: { opacity: 0 },
  show: {
    opacity: 1,
    transition: { staggerChildren: 0.08 }
  }
};

const item = {
  hidden: { opacity: 0, y: 20 },
  show: { opacity: 1, y: 0 }
};
```

### Spatial Composition

**Break the grid intentionally:**
```css
/* Asymmetric layouts */
.hero { 
  display: grid;
  grid-template-columns: 1fr 1.618fr; /* Golden ratio */
}

/* Overlap creates depth */
.overlap-up { margin-top: -4rem; position: relative; z-index: 10; }

/* Generous spacing hierarchy */
--space-section: clamp(4rem, 10vw, 8rem);
--space-block: clamp(2rem, 5vw, 4rem);
--space-element: clamp(1rem, 2vw, 2rem);
```

### Atmosphere & Texture

**Choose contextually:**

```css
/* Noise grain overlay */
.grain::after {
  content: '';
  position: fixed;
  inset: 0;
  background: url("data:image/svg+xml,%3Csvg viewBox='0 0 256 256' xmlns='http://www.w3.org/2000/svg'%3E%3Cfilter id='noise'%3E%3CfeTurbulence type='fractalNoise' baseFrequency='0.9' numOctaves='4' stitchTiles='stitch'/%3E%3C/filter%3E%3Crect width='100%25' height='100%25' filter='url(%23noise)'/%3E%3C/svg%3E");
  opacity: 0.03;
  pointer-events: none;
  z-index: 9999;
}

/* Gradient mesh (modern) */
.gradient-mesh {
  background: 
    radial-gradient(at 20% 80%, var(--color-accent) 0%, transparent 50%),
    radial-gradient(at 80% 20%, var(--color-secondary) 0%, transparent 50%),
    var(--color-surface);
}

/* Glassmorphism */
.glass {
  background: rgba(255,255,255,0.05);
  backdrop-filter: blur(12px);
  border: 1px solid rgba(255,255,255,0.1);
}

/* Subtle glow */
.glow {
  box-shadow: 
    0 0 20px rgba(var(--accent-rgb), 0.3),
    0 0 60px rgba(var(--accent-rgb), 0.1);
}
```

---

## Anti-Patterns (NEVER Do)

| ❌ Avoid | ✅ Instead |
|----------|-----------|
| `font-family: Inter, sans-serif` | Distinctive, context-appropriate font |
| Purple-to-blue gradient on white | Commit to a cohesive palette |
| Equal-width columns everywhere | Asymmetric, golden-ratio layouts |
| Generic card with shadow | Unique container treatment |
| Rounded-lg on everything | Varied, intentional border-radius |
| Animation on every element | Strategic motion at key moments |
| `#f5f5f5` gray backgrounds | Rich, atmospheric backgrounds |

---

## Output Checklist

Before delivering, verify:

- [ ] **Distinctive font pairing** — Would a designer choose this?
- [ ] **Cohesive color system** — CSS variables, not arbitrary values
- [ ] **One memorable element** — What will they remember?
- [ ] **Intentional motion** — Enhances, not decorates
- [ ] **Atmospheric depth** — Not flat or sterile
- [ ] **No generic patterns** — Doesn't look like default Tailwind/Bootstrap

---

## Quick Reference by Output Type

| Type | Key Focus | Motion | Complexity |
|------|-----------|--------|------------|
| **Landing Page** | Hero impact, scroll narrative | Page load + scroll reveals | High |
| **Dashboard** | Information hierarchy, scannability | Subtle feedback only | Medium |
| **Component** | Micro-interactions, polish | Hover/focus states | Medium |
| **Form** | Clarity, input feedback | Validation states | Low-Medium |
| **Portfolio** | Visual drama, personality | Gallery transitions | High |
| **Documentation** | Readability, navigation | Minimal | Low |

---

## Execution Philosophy

> **"Generic is the enemy. Every interface should feel like someone cared."**

The goal isn't maximum complexity — it's maximum intentionality. A three-color minimalist design with perfect spacing outperforms a messy gradient explosion. Commit to a direction. Execute it flawlessly. Ship something no one mistakes for AI-generated boilerplate.
