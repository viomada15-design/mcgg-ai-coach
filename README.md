# MCGG AI Coach — MVP 0.1

Android side-app scaffold for a **read-only gaming coach**. It observes the visible screen with user-approved MediaProjection, converts UI state into structured data, and shows recommendations in a floating overlay. It does not modify game memory or automatically tap/play the game.

## Phase 1 included
- overlay permission flow
- floating coach overlay service
- MediaProjection permission/service scaffold
- GameState model
- rule-based advisor seed
- visual ROI map derived from the provided gameplay video
- patch-aware knowledge-base seed format
- GitHub Actions cloud build workflow

## Next engineering steps
1. Implement VirtualDisplay + ImageReader sampling.
2. Add scene classifier (battle / prep / shop / reward / roster).
3. Add OCR/object detectors for gold, HP, level, synergy, shop and board.
4. Populate Commander/skill, hero, synergy, item and Go Go Card data for the active patch.
5. Add patch-aware strategy scoring.
6. Bind recommendations to the overlay.
7. Calibrate ROIs on the target Galaxy Tab / phone aspect ratio.

## Cloud build
Push this project to GitHub. The included workflow builds a debug APK on every push and exposes it as an Actions artifact.
