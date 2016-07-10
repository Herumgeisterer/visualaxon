#!/usr/bin/env bash

git branch -d gh-pages
git push origin --delete gh-pages

git checkout --orphan gh-pages

npm install && npm run build

cp -r dist ..
cp index.html ..

cd ..

git rm -f --cached -r .

git add index.html
git add -f dist
git add README.md
git add LICENSE.md

git commit -am 'init gh-pages'
git push -u origin gh-pages


git checkout -f master