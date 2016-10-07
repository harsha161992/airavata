/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.airavata.model.error;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)")
public class ValidationResults implements org.apache.thrift.TBase<ValidationResults, ValidationResults._Fields>, java.io.Serializable, Cloneable, Comparable<ValidationResults> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ValidationResults");

  private static final org.apache.thrift.protocol.TField VALIDATION_STATE_FIELD_DESC = new org.apache.thrift.protocol.TField("validationState", org.apache.thrift.protocol.TType.BOOL, (short)1);
  private static final org.apache.thrift.protocol.TField VALIDATION_RESULT_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("validationResultList", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ValidationResultsStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ValidationResultsTupleSchemeFactory());
  }

  private boolean validationState; // required
  private List<ValidatorResult> validationResultList; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    VALIDATION_STATE((short)1, "validationState"),
    VALIDATION_RESULT_LIST((short)2, "validationResultList");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // VALIDATION_STATE
          return VALIDATION_STATE;
        case 2: // VALIDATION_RESULT_LIST
          return VALIDATION_RESULT_LIST;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __VALIDATIONSTATE_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.VALIDATION_STATE, new org.apache.thrift.meta_data.FieldMetaData("validationState", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.VALIDATION_RESULT_LIST, new org.apache.thrift.meta_data.FieldMetaData("validationResultList", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ValidatorResult.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ValidationResults.class, metaDataMap);
  }

  public ValidationResults() {
  }

  public ValidationResults(
    boolean validationState,
    List<ValidatorResult> validationResultList)
  {
    this();
    this.validationState = validationState;
    setValidationStateIsSet(true);
    this.validationResultList = validationResultList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ValidationResults(ValidationResults other) {
    __isset_bitfield = other.__isset_bitfield;
    this.validationState = other.validationState;
    if (other.isSetValidationResultList()) {
      List<ValidatorResult> __this__validationResultList = new ArrayList<ValidatorResult>(other.validationResultList.size());
      for (ValidatorResult other_element : other.validationResultList) {
        __this__validationResultList.add(new ValidatorResult(other_element));
      }
      this.validationResultList = __this__validationResultList;
    }
  }

  public ValidationResults deepCopy() {
    return new ValidationResults(this);
  }

  @Override
  public void clear() {
    setValidationStateIsSet(false);
    this.validationState = false;
    this.validationResultList = null;
  }

  public boolean isValidationState() {
    return this.validationState;
  }

  public void setValidationState(boolean validationState) {
    this.validationState = validationState;
    setValidationStateIsSet(true);
  }

  public void unsetValidationState() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __VALIDATIONSTATE_ISSET_ID);
  }

  /** Returns true if field validationState is set (has been assigned a value) and false otherwise */
  public boolean isSetValidationState() {
    return EncodingUtils.testBit(__isset_bitfield, __VALIDATIONSTATE_ISSET_ID);
  }

  public void setValidationStateIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __VALIDATIONSTATE_ISSET_ID, value);
  }

  public int getValidationResultListSize() {
    return (this.validationResultList == null) ? 0 : this.validationResultList.size();
  }

  public java.util.Iterator<ValidatorResult> getValidationResultListIterator() {
    return (this.validationResultList == null) ? null : this.validationResultList.iterator();
  }

  public void addToValidationResultList(ValidatorResult elem) {
    if (this.validationResultList == null) {
      this.validationResultList = new ArrayList<ValidatorResult>();
    }
    this.validationResultList.add(elem);
  }

  public List<ValidatorResult> getValidationResultList() {
    return this.validationResultList;
  }

  public void setValidationResultList(List<ValidatorResult> validationResultList) {
    this.validationResultList = validationResultList;
  }

  public void unsetValidationResultList() {
    this.validationResultList = null;
  }

  /** Returns true if field validationResultList is set (has been assigned a value) and false otherwise */
  public boolean isSetValidationResultList() {
    return this.validationResultList != null;
  }

  public void setValidationResultListIsSet(boolean value) {
    if (!value) {
      this.validationResultList = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case VALIDATION_STATE:
      if (value == null) {
        unsetValidationState();
      } else {
        setValidationState((Boolean)value);
      }
      break;

    case VALIDATION_RESULT_LIST:
      if (value == null) {
        unsetValidationResultList();
      } else {
        setValidationResultList((List<ValidatorResult>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case VALIDATION_STATE:
      return isValidationState();

    case VALIDATION_RESULT_LIST:
      return getValidationResultList();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case VALIDATION_STATE:
      return isSetValidationState();
    case VALIDATION_RESULT_LIST:
      return isSetValidationResultList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ValidationResults)
      return this.equals((ValidationResults)that);
    return false;
  }

  public boolean equals(ValidationResults that) {
    if (that == null)
      return false;

    boolean this_present_validationState = true;
    boolean that_present_validationState = true;
    if (this_present_validationState || that_present_validationState) {
      if (!(this_present_validationState && that_present_validationState))
        return false;
      if (this.validationState != that.validationState)
        return false;
    }

    boolean this_present_validationResultList = true && this.isSetValidationResultList();
    boolean that_present_validationResultList = true && that.isSetValidationResultList();
    if (this_present_validationResultList || that_present_validationResultList) {
      if (!(this_present_validationResultList && that_present_validationResultList))
        return false;
      if (!this.validationResultList.equals(that.validationResultList))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_validationState = true;
    list.add(present_validationState);
    if (present_validationState)
      list.add(validationState);

    boolean present_validationResultList = true && (isSetValidationResultList());
    list.add(present_validationResultList);
    if (present_validationResultList)
      list.add(validationResultList);

    return list.hashCode();
  }

  @Override
  public int compareTo(ValidationResults other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetValidationState()).compareTo(other.isSetValidationState());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetValidationState()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.validationState, other.validationState);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetValidationResultList()).compareTo(other.isSetValidationResultList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetValidationResultList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.validationResultList, other.validationResultList);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ValidationResults(");
    boolean first = true;

    sb.append("validationState:");
    sb.append(this.validationState);
    first = false;
    if (!first) sb.append(", ");
    sb.append("validationResultList:");
    if (this.validationResultList == null) {
      sb.append("null");
    } else {
      sb.append(this.validationResultList);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetValidationState()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'validationState' is unset! Struct:" + toString());
    }

    if (!isSetValidationResultList()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'validationResultList' is unset! Struct:" + toString());
    }

    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ValidationResultsStandardSchemeFactory implements SchemeFactory {
    public ValidationResultsStandardScheme getScheme() {
      return new ValidationResultsStandardScheme();
    }
  }

  private static class ValidationResultsStandardScheme extends StandardScheme<ValidationResults> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ValidationResults struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // VALIDATION_STATE
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.validationState = iprot.readBool();
              struct.setValidationStateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // VALIDATION_RESULT_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.validationResultList = new ArrayList<ValidatorResult>(_list0.size);
                ValidatorResult _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = new ValidatorResult();
                  _elem1.read(iprot);
                  struct.validationResultList.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setValidationResultListIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, ValidationResults struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(VALIDATION_STATE_FIELD_DESC);
      oprot.writeBool(struct.validationState);
      oprot.writeFieldEnd();
      if (struct.validationResultList != null) {
        oprot.writeFieldBegin(VALIDATION_RESULT_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.validationResultList.size()));
          for (ValidatorResult _iter3 : struct.validationResultList)
          {
            _iter3.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ValidationResultsTupleSchemeFactory implements SchemeFactory {
    public ValidationResultsTupleScheme getScheme() {
      return new ValidationResultsTupleScheme();
    }
  }

  private static class ValidationResultsTupleScheme extends TupleScheme<ValidationResults> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ValidationResults struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeBool(struct.validationState);
      {
        oprot.writeI32(struct.validationResultList.size());
        for (ValidatorResult _iter4 : struct.validationResultList)
        {
          _iter4.write(oprot);
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ValidationResults struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.validationState = iprot.readBool();
      struct.setValidationStateIsSet(true);
      {
        org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        struct.validationResultList = new ArrayList<ValidatorResult>(_list5.size);
        ValidatorResult _elem6;
        for (int _i7 = 0; _i7 < _list5.size; ++_i7)
        {
          _elem6 = new ValidatorResult();
          _elem6.read(iprot);
          struct.validationResultList.add(_elem6);
        }
      }
      struct.setValidationResultListIsSet(true);
    }
  }

}

