Campsite Commander is a camping inventory app that allows users to add, view, and manage gear items. The app uses arrays to store item information and displays a checklist of equipment for outdoor trips.

 Psuedo Code:
 START

Splash Activity

OUTPUT campfire logo
OUTPUT "Campsite Commander"
OUTPUT student details

WAIT 3000 milliseconds

OPEN MainActivity
CLOSE SplashActivity

Main Activity

DECLARATIONS

itemNames[20] : STR
itemCategories[20] : STR
itemQuantities[20] : NUM
itemNotes[20] : STR

itemCount : NUM
counter : NUM
currentIndex : NUM

btnAddGear : BUTTON
btnViewChecklist : BUTTON
btnClearPack : BUTTON
btnExit : BUTTON

txtName : TEXTBOX
spnCategory : SPINNER
txtQuantity : NUMERIC TEXTBOX
txtNotes : TEXTBOX

SET itemCount = 0

PROCESS

Load sample data
SET itemCount = 5

OUTPUT "Total Items: " + itemCount

WHEN btnAddGear IS CLICKED

    INPUT txtName
    INPUT spnCategory
    INPUT txtQuantity
    INPUT txtNotes

    IF txtName = "" THEN
        OUTPUT "Enter item name"
        STOP
    ENDIF

    IF txtQuantity = "" THEN
        OUTPUT "Enter quantity"
        STOP
    ENDIF

    IF txtQuantity <= 0 THEN
        OUTPUT "Quantity must be positive"
        STOP
    ENDIF

    IF itemCount >= 20 THEN
        OUTPUT "Pack Full"
        STOP
    ENDIF

    itemNames[itemCount] = txtName
    itemCategories[itemCount] = spnCategory
    itemQuantities[itemCount] = txtQuantity
    itemNotes[itemCount] = txtNotes

    itemCount = itemCount + 1

    OUTPUT "Item Added"
    OUTPUT "Total Items: " + itemCount

    CLEAR txtName
    CLEAR txtQuantity
    CLEAR txtNotes

END WHEN

WHEN btnViewChecklist IS CLICKED

    IF itemCount = 0 THEN
        OUTPUT "Pack Empty"
    ELSE
        OPEN DetailActivity
    ENDIF

END WHEN

WHEN btnClearPack IS CLICKED

    FOR counter ← 0 TO 19

        itemNames[counter] = ""
        itemCategories[counter] = ""
        itemQuantities[counter] = 0
        itemNotes[counter] = ""

    ENDFOR

    itemCount = 0

    OUTPUT "Pack Cleared"
    OUTPUT "Total Items: 0"

END WHEN

WHEN btnExit IS CLICKED

    CLOSE APPLICATION

END WHEN

Detail Activity

DECLARATIONS

currentIndex : NUM

btnNext : BUTTON
btnPrevious : BUTTON
btnBackToBase : BUTTON

SET currentIndex = 0

DISPLAY itemNames[currentIndex]
DISPLAY itemCategories[currentIndex]
DISPLAY itemQuantities[currentIndex]
DISPLAY itemNotes[currentIndex]

WHEN btnNext IS CLICKED

    IF currentIndex < itemCount - 1 THEN

        currentIndex = currentIndex + 1

        DISPLAY itemNames[currentIndex]
        DISPLAY itemCategories[currentIndex]
        DISPLAY itemQuantities[currentIndex]
        DISPLAY itemNotes[currentIndex]

    ELSE

        OUTPUT "Last Item"

    ENDIF

END WHEN

WHEN btnPrevious IS CLICKED

    IF currentIndex > 0 THEN

        currentIndex = currentIndex - 1

        DISPLAY itemNames[currentIndex]
        DISPLAY itemCategories[currentIndex]
        DISPLAY itemQuantities[currentIndex]
        DISPLAY itemNotes[currentIndex]

    ELSE

        OUTPUT "First Item"

    ENDIF

END WHEN

WHEN btnBackToBase IS CLICKED

    OPEN MainActivity
    CLOSE DetailActivity

END WHEN

END APPLICATION
