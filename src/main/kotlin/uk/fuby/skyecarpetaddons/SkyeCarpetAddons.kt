package uk.fuby.skyecarpetaddons

import carpet.CarpetExtension
import carpet.CarpetServer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.internal.readJson
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory
import java.io.File
import java.io.IOException


object SkyeCarpetAddons : ModInitializer, CarpetExtension {
    private val logger = LoggerFactory.getLogger("skye-carpet-addons")

	override fun onInitialize() {
		CarpetServer.manageExtension(this)
	}

	override fun version(): String {
		return "Skye-Carpet-Addons"
	}

	override fun onGameStarted() {
		CarpetServer.settingsManager.parseSettingsClass(Options.javaClass)
	}

	override fun canHasTranslations(lang: String?): MutableMap<String, String> {
		return getTranslationFromResourcePath(lang)
	}

	private fun getTranslationFromResourcePath(lang: String?): MutableMap<String, String> {
		lang ?: return mutableMapOf()
		val langFilePath = this.javaClass.classLoader.getResource("assets/skye-carpet-addons/lang/$lang.json")?.path
		langFilePath ?: return mutableMapOf()
		val langFile = File(langFilePath)
		println(langFile.toPath())
		if (!langFile.exists() || langFile.isDirectory) return mutableMapOf()
		val jsonData: String = try {
			langFile.readText()
		} catch (e: IOException) {
			return mutableMapOf()
		}
		return Json.decodeFromJsonElement<Map<String, String>>(Json.parseToJsonElement(jsonData)).toMutableMap()
	}
}