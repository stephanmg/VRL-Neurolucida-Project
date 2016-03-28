# VRL-Neurolucida-Project
Neurolucida Tools for VRL and UG.

Converts Neurolucida (XML) files to mesh files (UGX) suitable for the PDE solver framework [UG](https://github.com/UG4).

## Example
![](/resources/img/example.png)

## Code metrics
[![Codacy Badge](https://api.codacy.com/project/badge/grade/414dbeeab3734c43aae6776f4f3b2f0b)](https://www.codacy.com/app/stephan_5/VRL-Neurolucida-Project)

## Build & Run
Build ug4 backend with the Neurolucida plugin activated, then build VRL-UG-API (cf. [UG](https://github.com/UG4) and [Neurobox](https://github.com/NeuroBox3D)) and build respectively install the VRL-Neurolucida-Project plugin by the predefined installVRLPlugin task within the Gradle project (before you should make sure set up [VRL Studio](https://github.com/miho/VRL-Studio) accordingly) for VRL-Studio.
