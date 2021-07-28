package app.el_even.priceupdater.configs

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import app.el_even.priceupdater.models.local.Product
import com.squareup.picasso.Picasso
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Product>?) {
    val adapter = recyclerView.adapter as ProductAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val uri = imgUrl.toUri().buildUpon().scheme("https").build()
        Picasso.get().load(uri).into(imgView)
    }
}

/**
 * Function allows to decode html content and retrieve the attribute value
 *
 * @param html: HTML content
 * @param attribute: HTML attribute value
 * @param name
 * @return Elements HTML
 */
fun getElementsByAttributeValue(html: String, attribute: String, name: String): Elements =
    Jsoup.parse(html).html(html).getElementsByAttributeValue(attribute, name)


/**
 * Function allows to decode html content and retrieve the class value
 *
 * @param html: HTML content
 * @param className: HTML class
 * @return Elements HTML
 */
fun getElementsByClass(html: String, className: String): Elements =
    Jsoup.parse(html).html(html).getElementsByClass(className)

/**
 * Function allows to decode html content and retrieve the tag value
 *
 * @param html: HTML content
 * @param tag: HTML tag
 * @return Elements HTML
 */
fun getElementsByTag(html: String, tag: String): Elements =
    Jsoup.parse(html).html(html).getElementsByTag(tag)

/**
 * Function allows to decode html content and retrieve the id value
 *
 * @param html: HTML content
 * @param id: HTML id
 * @return Elements HTML
 */
fun getElementsById(html: String, id: String): Element? =
    Jsoup.parse(html).html(html).getElementById(id)
