#!/bin/bash

fswatch -e ".git" -0 /Users/makcuzlov/Downloads/Lab4_2 | xargs -0 -n 1 bash /Users/makcuzlov/Downloads/Lab4_2/.scripts/commit-all.sh
