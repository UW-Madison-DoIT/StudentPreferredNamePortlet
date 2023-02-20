# Change log

## Next

Now building 2.0.3-SNAPSHOT.

## 2.0.2 - 2023-02-20

+ Usability improvements:
  + focus on name-in-use edit form fields when displaying edit form.
  + Visually indicate focus on clickable elements.
  + Increase color contrast of some text.
  + Increase some font sizes.
+ use myuw-parent 10 which publishes artifacts to and additionally sources artifacts from artifactorydoit.jfrog.io

## 2.0.1

2022-05-31

+ Reflect re-brand to Name in Use in last name similarity requirement instructions.

## 2.0.0

2022-05-16

+ Re-brand to Name in Use
+ Support Latin-9 character set, when feature flag `preferred-name-allow-latin9` is true.
  This is intended to support accent marks in preferred names, like André, François, Nuñez, etc.
+ Allow any preferred last name within the character set, when `preferred-name-allow-any-last-name` is true.
  This is intended to allow self-service setting of preferred last name for people who use a professional last name
  different from their legal last name (for example some academics who marry and change last name but have
  developed a reputation under their prior name) and for people navigating life events wherein legal name might
  lag behind or not match their preference in how to be named in the university setting
  (for example: marriage, separation, divorce, partnership).
+ Changes validation error message to stop telling people their names are invalid. It's not you, it's us.

## 1.0.10 and earlier

These versions predate the change log.
