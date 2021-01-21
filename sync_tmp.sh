#!/bin/bash - 
#===============================================================================
#
#          FILE: sync_tmp.sh
# 
#         USAGE: ./sync_tmp.sh 
# 
#   DESCRIPTION: 
# 
#       OPTIONS: ---
#  REQUIREMENTS: ---
#          BUGS: ---
#         NOTES: ---
#        AUTHOR: KevinXie
#  ORGANIZATION: 
#       CREATED: 01/22/2021 00:36
#      REVISION:  ---
#===============================================================================
git=git
# if command -v proxychains4 >/dev/null 2>&1; then 
#     git='proxychains4 git'
# fi

$git add --all

$git diff --cached --exit-code > /dev/null 2>&1

if [ $? -ne 0 ]; then
$git commit -m "`date +\"%m-%d %T\"` commited by `hostname`

changelog:
`git status -s`
"
fi

$git pull --rebase

$git push
