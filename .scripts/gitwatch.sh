#!/bin/bash

fswatch -e ".git" -0 /Users/makcuzlov/Lab4_2 | xargs -0 -n 1 bash /Users/makcuzlov/Lab4_2/.scripts/commit-all.sh
