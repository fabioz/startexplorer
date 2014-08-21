### General instructions

* Check the README.markdown to make sure you have the necessary projects setup in
  Eclipse. Publishing a new version to the update site requires the plugin subfolder,
  the feature subfolder and the gh-pages branch each as a *separate* project in Eclipse.

* Check that help is up to date (did you implement new features?)
* Check that new features are described in release-notes-upcoming.txt or README.markdown

* Go to de.bastiankrol.startexplorer/META-INF/MANIFEST.INF
* Set Bundle-Version to the new version number, like 1.x.y.qualifier (qualifier as literal)
* OR: In tab Overview, change field Version, that's the same
* Go to de.bastiankrol.startexplorer-feature/feature.xml, Tab Overview.
* Enter the same version number as above in the Version field, without the .qualifier.
* Go to startexplorer-gh-pages/update/site.xml, tab site.xml
* Correct the version number in the jar referenced in the url attribute as well
  as in the version attribute
* Go to startexplorer-gh-pages/update/site.xml, tab Site Map.
* Click "Build All" (if this does not do anything, it might be because that you have not
  set up the feature as a seperate project in Eclipse).
* Check that new jars with the corresponding versions have been build in
  startexplorer-gh-pages/update/plugins and
  startexplorer-gh-pages/update/features
* Search for the version number in the four pom.xml files for the Maven/Tycho build and
  replace all occurences.
* Commit and push all changes

* If you want to check if the new version can be correctly installed through the
  Eclipse update mechanism, beware: if you contacted the update site before the
  upload, you might need to restart Eclipse to see the new data from the remote
  update site. Eclipse seems to cache this information.

* Move everything from release-notes-upcoming.txt to README.markdown
* Check if there are any new features, bugfixes etc. that had not been entered
  in release-notes-upcoming.txt, add them to README.markdown, too.
* Commit and push README.markdown

* If help was updated, copy it to gh-pages & commit & push

* Update Eclipse Marketplace entry at https://marketplace.eclipse.org/node/641101
