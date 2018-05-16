package com.hp.kalexa.model.payload.print

import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeName("ImageJPEG")
class ImageJPEG(title: String = "", description: String = "", url: String = "") : Print.PrintType(title, description, url)