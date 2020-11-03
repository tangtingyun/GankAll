package com.step.toolkit.fragments.bottom

class ItemBuilder {
    private val mItems =
        LinkedHashMap<BottomTabBean, BottomItemFragment>()

    companion object {
        internal fun builder(): ItemBuilder {
            return ItemBuilder()
        }
    }


    fun addItem(bean: BottomTabBean, fragment: BottomItemFragment)
            : ItemBuilder {
        mItems[bean] = fragment
        return this
    }

    fun addItems(items: LinkedHashMap<BottomTabBean, BottomItemFragment>)
            : ItemBuilder {
        mItems.putAll(items)
        return this
    }

    fun build(): LinkedHashMap<BottomTabBean, BottomItemFragment> {
        return mItems
    }
}