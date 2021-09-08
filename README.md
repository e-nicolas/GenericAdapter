# Generic Adapter

GenericAdapter is an easy way to implement RecyclerView adapters with more flexibility.

## Features
- [x] LayoutRes or binding support
- [x] Multiples viewTypes inside adapter
- [x] Automatic DiffUtil
- [x] Header support 
- [x] Multiples sections support
- [ ] Footer support

## Installation

#### Gradle
Step 1. Add it in your root build.gradle at the end of repositories:
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
Step 2. Add the dependency
```groovy
implementation 'com.github.e-nicolas:GenericAdapter:v1.0.7'
```

#### Manual
1. Download the project and copy ExtendedPlayerView directory to your project
2. Add the following line to your app's gradle:

```groovy
implementation project(':genericadapter')
```

# Usage

### Single Section Adapter
```kotlin
/**
 * Adapter delegate
 */
fun setupAdapter() {
    val adapter = GenericRecyclerAdapter()
    adapter.delegate = recyclerDelegate
    recyclerView.adapter = adapter
}

/**
 * Adapter delegate
 */
private val recyclerDelegate = object : GenericRecylerAdapterDelegate {
    override fun cellForPosition(
        adapter: GenericRecyclerAdapter, 
        cell: RecyclerView.ViewHolder, 
        position: Int) {
        // Configure your cell
        val firstCell = cell as? FirstCell
        val item = viewModel.list[position]
        firstCell?.binding?.textView?.text = item
    }

    override fun registerCellAtPosition(
        adapter: GenericRecyclerAdapter, 
        position: Int): AdapterHolderType {
        // Return the cell that you want to inflate
        return AdapterHolderType(
            viewBinding = CellFirstBinding::class.java,
            clazz = FirstCell::class.java,
            reuseIdentifier = 0
        )
    }

    override fun didSelectItemAtIndex(
        adapter: GenericRecyclerAdapter, 
        index: Int) {
        // Cell selection
        val selectedItem = viewModel.list[index]
    }

    override fun numberOfRows(adapter: GenericRecyclerAdapter): Int {
        // Number of items inside RecyclerView
        return viewModel.list.size
    }
}
```

### Multiple Sections Adapter

```kotlin
/**
 * Adapter creation
 */
fun setupAdapter() {
    val adapter = GenericRecyclerSections()
    adapter.delegate = recyclerDelegate
    recyclerView.adapter = adapter
}

/**
 * Adapter delegate
 */
private val recyclerDelegate = object : SectionDelegate {

    override fun numberOfSections(): Int {
        return viewModel.sections.size
    }

    override fun numberOfRowsInSection(section: Int): Int {
        return viewModel.sections[section].list.size
    }

    override fun cellForRowAt(
        indexPath: IndexPath, 
        cell: RecyclerView.ViewHolder) {
        // Configure your cell
        val sectionModel = viewModel.sections[indexPath.section]
        val list = sectionModel.list[indexPath.row]
        val text = getString(R.string.itemString, list.toString())
        (cell as? FirstCell)?.binding?.textView?.text = text
    }

    override fun registerCellAt(indexPath: IndexPath): AdapterHolderType {
        // Return the cell that you want
        return AdapterHolderType(
                viewBinding = CellFirstBinding::class.java,
                clazz = FirstCell::class.java,
                reuseIdentifier = 0
            )
    }

    override fun registerHeaderForSection(section: Int): AdapterHolderType {
        // Return the header that you want
        return AdapterHolderType(
            viewBinding = CellHeaderBinding::class.java,
            clazz = HeaderCell::class.java,
            reuseIdentifier = 2
        )
    }

    override fun viewForHeaderInSection(section: Int, header: RecyclerView.ViewHolder) {
        // Configure your header
        (header as? HeaderCell)?.binding?.textView?.text = viewModel.sections[section].title
    }

    override fun didSelectRowAt(indexPath: IndexPath) {
        // Cell selection
    }
}
```

### Using Diff
Create your adapter with Snapshot()
```kotlin
val adapter = GenericRecyclerSections(Snapshot())
adapter.delegate = recyclerDelegate
recyclerView.adapter = adapter
// First list state
adapter.snapshot?.updateSnapshot(viewModel.list)
// Change list
viewModel.list.removeFirst()
// When list changed call it again
adapter.snapshot?.updateSnapshot(viewModel.list)

```

## Contributions
Please contribute! I will gladly review any pull requests.

## License

```
Copyright 2021 Emmanouil Nicolas.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```