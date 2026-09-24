# Gameplay reference mapping — 10232.mp4

Reference video: 854×480, 876.74 seconds (~14m37s).

Observed scene classes from 30-second sampling:
- combat board
- preparation / bench management
- hero shop / lineup selection
- reward / choice screen
- Spirit Sand shop
- roster / hero list screen
- player standings / HP panel

Stable visual zones suitable for first-pass detection:
- left: synergy/status icons
- center: board or modal choice panel
- bottom: bench / owned units
- lower-right: economy/level controls
- right: opponent standings / HP

The detector should classify the current scene first, then run scene-specific recognition. This is cheaper and more reliable than asking a vision LLM to interpret every frame.
