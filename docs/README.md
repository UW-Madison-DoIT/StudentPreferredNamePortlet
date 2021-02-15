# "Student" Preferred Name Portlet

## Not just for students

There's nothing specifically "student" about the Preferred Name Portlet except
that was its original name and branding.
It continues to be available to students in MyUW as [Student Personal Information][], though the only "personal information" it shows is system of record name and preferred name.
The identical code is also published under the name [Preferred Name][]
and available to a wider audience including staff.
Historically the staff-only [Madison "Personal Information" portlet][]
in the HRS suite
included another implementation of preferred name viewing and editing,
but in modern MyUW Personal Information displays preferred name to Madison users
but hyperlinks to Preferred Name for preferred name editing.

While Madison and System "Personal Information" are the same source code,
they respond differently to Madison vs System identity information such that
"Personal Information" only provided and provides a UI for preferred name for
Madison users.

## Architecture

The Preferred Name Portlet provides the user interface for eligible users to
view and modify their "preferred name" on a self-service basis. The Portlet
connects to back-end systems from CIAM to read and write this data. The Portlet
holds no data -- it is only an interface for viewing and modifying CIAM data.

## End-User interface

Preferred Name shows the user their current system-of-record name and their
preferred name, if any.

![Screenshot of Preferred Name portlet showing a preferred name already set](./media/preferred-name-showing-name-set.png)

Users can "Change" or "Delete" their preferred name.

"Change" opens a form.

![Screenshot of Preferred Name portlet showing a form for self-service editing preferred name](./media/preferred-name-edit-ui-open.png)

From the form, users can edit their preferred name.

There's some form validation.
The preferred last name can only be edited to a value that differs from the
current preferred last name
(or current system of record last name if no current preferred name)
by capitalization, spacing, apostrophes, and hyphens.
Using a different last name, etc., is not permitted.

![Screenshot of Preferred Name portlet showing an error on attempting to edit last name to a disallowed value](./media/preferred-name-disallowed-last-name.png)

Also no part of the preferred name can have characters other than A-Z, a-z, spaces, apostrophes, and hyphens. Accented characters are not permitted.

![Screenshot of Preferred Name portlet showing form validation fail on attempt to use the first name André](./media/preferred-name-disallowed-character.png)

## Admin interface

There's an administrative interface whereby authorized administrators can
look up the preferred name of a person by NetID or by PVI.

![Screenshot of preferred name administration lookup form](./media/preferred-name-admin-lookup-form.png)

The administrative interface is *not* an interface for setting the user's
preferred name. The concept is that preferred name is self-service and only
users can choose their own preferred name.

![Screenshot showing Andrew Petro's preferred name as looked up in admin interface](./media/preferred-name-admin-view.png)

In the administrative interface, an administrator can delete a preferred name.

![Screenshot showing delete button in preferred name admin](./media/preferred-name-admin-delete.png)

Administrators can also toggle whether to hide the primary/legal name (in directory search results?)



[Student Personal Information]: https://my.wisc.edu/web/apps/details/StudentPreferredName
[Preferred Name]: https://my.wisc.edu/web/apps/details/preferred-name
[Madison "Personal Information" portlet]: https://my.wisc.edu/web/apps/details/contact-information
