/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.vertx.realworld.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link io.vertx.realworld.model.TemporaryLink}.
 *
 * NOTE: This class has been automatically generated from the {@link io.vertx.realworld.model.TemporaryLink} original class using Vert.x codegen.
 */
public class TemporaryLinkConverter {

  public static void fromJson(JsonObject json, TemporaryLink obj) {
    if (json.getValue("createdDate") instanceof String) {
      obj.setCreatedDate((String)json.getValue("createdDate"));
    }
    if (json.getValue("isActive") instanceof Boolean) {
      obj.setIsActive((Boolean)json.getValue("isActive"));
    }
    if (json.getValue("link") instanceof String) {
      obj.setLink((String)json.getValue("link"));
    }
    if (json.getValue("updatedDate") instanceof String) {
      obj.setUpdatedDate((String)json.getValue("updatedDate"));
    }
  }

  public static void toJson(TemporaryLink obj, JsonObject json) {
    if (obj.getCreatedDate() != null) {
      json.put("createdDate", obj.getCreatedDate());
    }
    if (obj.getIsActive() != null) {
      json.put("isActive", obj.getIsActive());
    }
    if (obj.getLink() != null) {
      json.put("link", obj.getLink());
    }
    if (obj.getUpdatedDate() != null) {
      json.put("updatedDate", obj.getUpdatedDate());
    }
  }
}