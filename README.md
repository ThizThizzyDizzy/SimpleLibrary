# SimpleLibraryPlus
A fork of SimpleLibrary, containing bug fixes and some more specialized features

## Major changes:
### General changes
- Removed all references to AWT outside of `WindowHelper`
### GUI
- Added Tooltip support to `Menu` and `MenuComponent`
- Added MenuComponentPanel
### Config
- Added `byte`, `short`, and `ConfigNumberList` support to `config2.ConfigList`
- Added typed `get` methods to `config2.Config` and `config2.ConfigList`
### Rendering
- Replaced missing texture image with a magenta/black tile
- Replaced AWT BufferedImage/Color classes with built-in classes
- STB is now used to load images instead of ImageIO
- Added word wrapping methods
- Added basic .obj model rendering
- Added a Depth buffer to framebuffers