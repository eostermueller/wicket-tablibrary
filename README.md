wicket-tablibrary
=================

A work-in-progress to enhance the Wicket "library" example.  The new example will show the BookDetails in a page with multiple tabs.

The original "library" example is here:
http://www.wicket-library.com/wicket-examples-6.0.x/library/wicket/bookmarkable/org.apache.wicket.examples.library.SignIn

Once you've forked this repo and done "mvn package", start the example using

```
localhost:8080/wicket-examples/tablibrary/
```

I'm a beginner with wicket and could use some help getting by this problem.
The app starts fine, just like the wicket "libray".
But when you select one of the books in the initial list, 
PageTabbedPanel displays the content for one tab, but none of the tabs themselves.

Expected behavior:  once you select a book, you see details for that book in one 'active' tab, along with two other inactive tabs.
Current behavior:  once you select a book, you see details for that book but none of the 3 tabs.

Thanks in advance for any help on this.

--Erik

