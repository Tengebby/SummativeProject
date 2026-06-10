# Campsite Commander - IMAD5112 Practicum

## Student Details
- **Name:** Your Name Here
- **Student Number:** ST10529833
- **Module:** IMAD5112 – Introduction to Mobile Application Development
- **GitHub Repository:** [https://github.com/ST10529833/CampsiteCommander](https://github.com/ST10529833/CampsiteCommander) *(update this link)*

---

## Purpose of the App

Campsite Commander is a native Android inventory app for outdoor adventures. It helps campers manage their packing list by:

- Adding gear items with a name, category (Shelter, Cooking, First Aid, Clothing, Navigation, Other), quantity, and notes.
- Storing all data in parallel arrays (itemNames, itemCategories, itemQuantities, itemNotes).
- Using a loop to calculate the total number of items packed.
- Providing a detailed checklist view showing each item's full information.
- Pre-loading sample data so users have a starting point.
- Allowing users to clear the entire pack and re-enter data.

---

## Design Considerations

- **Dark mode / nature theme:** Background `#1B2A1B` (deep forest green), accent `#4CAF50` (leaf green), text `#A5D6A7`.
- **Three-screen architecture:** Splash (3s auto-transition) → Main → Detail.
- **Parallel arrays:** Four arrays of size 20 track name, category, quantity, and notes per item.
- **Loop-based total:** A `for` loop sums all `itemQuantities[]` entries to display total units packed.
- **Category colour badges:** Each category has a distinct colour in the detail view for quick identification.
- **Error handling:** Empty fields, non-numeric/zero quantities, and full-list scenarios all show Toast messages.
- **Logging:** `Log.d` and `Log.e` throughout for code understanding and debugging.

---

## Pseudocode

```
START App
  SHOW SplashActivity
    DISPLAY campfire emoji logo, "Campsite Commander" title, student info
    WAIT 3000ms
    NAVIGATE to MainActivity, CLOSE SplashActivity

  SHOW MainActivity
    DECLARE arrays: itemNames[20], itemCategories[20], itemQuantities[20], itemNotes[20]
    DECLARE itemCount = 0
    LOAD sampleData() → pre-fill first 5 items into arrays, itemCount = 5
    DISPLAY total items using loop

    WHEN user clicks "Add Gear":
      READ name, category (spinner), quantity, notes from inputs
      IF name is empty THEN show error Toast, STOP
      IF quantity is empty THEN show error Toast, STOP
      IF quantity is not a positive integer THEN show error Toast, STOP
      IF itemCount >= 20 THEN show "Pack full" Toast, STOP
      STORE in arrays at index itemCount
      itemCount++
      UPDATE total display using loop
      CLEAR input fields

    WHEN user clicks "View Full Checklist":
      IF itemCount == 0 THEN show "Pack empty" Toast, STOP
      PASS arrays + itemCount to DetailActivity via Intent

    WHEN user clicks "Clear Pack":
      RESET all arrays to empty/zero
      SET itemCount = 0
      UPDATE total display

    WHEN user clicks "Exit":
      CLOSE app

  SHOW DetailActivity
    RECEIVE itemNames[], itemCategories[], itemQuantities[], itemNotes[], itemCount from Intent
    SET currentIndex = 0
    DISPLAY item at currentIndex

    WHEN user clicks "Next":
      IF currentIndex < itemCount - 1 THEN currentIndex++, refresh display
      ELSE show "Last item" Toast

    WHEN user clicks "Previous":
      IF currentIndex > 0 THEN currentIndex--, refresh display
      ELSE show "First item" Toast

    WHEN user clicks "Back to Base":
      RETURN to MainActivity

END App
```

---

## Screenshots

> *(Add screenshots after running on emulator)*

### Screen 1 – Splash Screen
- Deep forest green background with campfire emoji logo.
- App title "Campsite Commander", tagline, student name and number.
- Auto-transitions to Main after 3 seconds.

### Screen 2 – Main Screen
- Pack Status card showing total entries and total units.
- Add Gear form: Item Name, Category spinner, Quantity, Notes.
- Buttons: Add Gear, View Full Checklist, Clear Pack, Exit App.

### Screen 3 – Detail / Checklist Screen
- One item displayed at a time in a dark card.
- Colour-coded category badge (green=Shelter, orange=Cooking, red=First Aid, etc.)
- Previous / Next to cycle through items.
- "Back to Base" returns to Main screen.

### Error Messages
- *"Please enter an item name."* – empty name field
- *"Please enter a quantity."* – empty quantity field
- *"Quantity must be a positive whole number."* – invalid quantity
- *"Packing list is full! Max 20 items allowed."* – array capacity reached
- *"Your pack is empty! Add some gear first."* – viewing details with no items
