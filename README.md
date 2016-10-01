# Assignment_3

Files that are deleted on deleting the application:-
Files stored in the internal storage get deleted when the user uninstalls the application.
Files are removed from external storage on deletion only when they were saved using getExternalFilesDir().

Files that are not deleted on deleting the application:-
Files stored in the external storage get deleted when the user uninstalls the application.

Files that can be used by other third-party applications:-
Files that are stored using Private Mode are kept private to the application.
