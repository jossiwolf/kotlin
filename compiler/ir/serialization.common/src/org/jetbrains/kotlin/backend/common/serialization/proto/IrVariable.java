// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: compiler/ir/serialization.common/src/KotlinIr.proto

package org.jetbrains.kotlin.backend.common.serialization.proto;

/**
 * Protobuf type {@code org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable}
 */
public final class IrVariable extends
    org.jetbrains.kotlin.protobuf.GeneratedMessageLite implements
    // @@protoc_insertion_point(message_implements:org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable)
    IrVariableOrBuilder {
  // Use IrVariable.newBuilder() to construct.
  private IrVariable(org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder builder) {
    super(builder);
    this.unknownFields = builder.getUnknownFields();
  }
  private IrVariable(boolean noInit) { this.unknownFields = org.jetbrains.kotlin.protobuf.ByteString.EMPTY;}

  private static final IrVariable defaultInstance;
  public static IrVariable getDefaultInstance() {
    return defaultInstance;
  }

  public IrVariable getDefaultInstanceForType() {
    return defaultInstance;
  }

  private final org.jetbrains.kotlin.protobuf.ByteString unknownFields;
  private IrVariable(
      org.jetbrains.kotlin.protobuf.CodedInputStream input,
      org.jetbrains.kotlin.protobuf.ExtensionRegistryLite extensionRegistry)
      throws org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException {
    initFields();
    int mutable_bitField0_ = 0;
    org.jetbrains.kotlin.protobuf.ByteString.Output unknownFieldsOutput =
        org.jetbrains.kotlin.protobuf.ByteString.newOutput();
    org.jetbrains.kotlin.protobuf.CodedOutputStream unknownFieldsCodedOutput =
        org.jetbrains.kotlin.protobuf.CodedOutputStream.newInstance(
            unknownFieldsOutput, 1);
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!parseUnknownField(input, unknownFieldsCodedOutput,
                                   extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
          case 10: {
            org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase.Builder subBuilder = null;
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
              subBuilder = base_.toBuilder();
            }
            base_ = input.readMessage(org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase.PARSER, extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(base_);
              base_ = subBuilder.buildPartial();
            }
            bitField0_ |= 0x00000001;
            break;
          }
          case 16: {
            bitField0_ |= 0x00000002;
            name_ = input.readInt32();
            break;
          }
          case 24: {
            bitField0_ |= 0x00000004;
            type_ = input.readInt32();
            break;
          }
          case 32: {
            bitField0_ |= 0x00000008;
            isVar_ = input.readBool();
            break;
          }
          case 40: {
            bitField0_ |= 0x00000010;
            isConst_ = input.readBool();
            break;
          }
          case 48: {
            bitField0_ |= 0x00000020;
            isLateinit_ = input.readBool();
            break;
          }
          case 58: {
            org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression.Builder subBuilder = null;
            if (((bitField0_ & 0x00000040) == 0x00000040)) {
              subBuilder = initializer_.toBuilder();
            }
            initializer_ = input.readMessage(org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression.PARSER, extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(initializer_);
              initializer_ = subBuilder.buildPartial();
            }
            bitField0_ |= 0x00000040;
            break;
          }
          case 66: {
            bitField0_ |= 0x8;
            isClassDelegate_ = input.readBool();
            break;
          }
        }
      }
    } catch (org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException(
          e.getMessage()).setUnfinishedMessage(this);
    } finally {
      try {
        unknownFieldsCodedOutput.flush();
      } catch (java.io.IOException e) {
      // Should not happen
      } finally {
        unknownFields = unknownFieldsOutput.toByteString();
      }
      makeExtensionsImmutable();
    }
  }
  public static org.jetbrains.kotlin.protobuf.Parser<IrVariable> PARSER =
      new org.jetbrains.kotlin.protobuf.AbstractParser<IrVariable>() {
    public IrVariable parsePartialFrom(
        org.jetbrains.kotlin.protobuf.CodedInputStream input,
        org.jetbrains.kotlin.protobuf.ExtensionRegistryLite extensionRegistry)
        throws org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException {
      return new IrVariable(input, extensionRegistry);
    }
  };

  @java.lang.Override
  public org.jetbrains.kotlin.protobuf.Parser<IrVariable> getParserForType() {
    return PARSER;
  }

  private int bitField0_;
  public static final int BASE_FIELD_NUMBER = 1;
  private org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase base_;
  /**
   * <code>required .org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase base = 1;</code>
   */
  public boolean hasBase() {
    return ((bitField0_ & 0x00000001) == 0x00000001);
  }
  /**
   * <code>required .org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase base = 1;</code>
   */
  public org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase getBase() {
    return base_;
  }

  public static final int NAME_FIELD_NUMBER = 2;
  private int name_;
  /**
   * <code>required int32 name = 2;</code>
   */
  public boolean hasName() {
    return ((bitField0_ & 0x00000002) == 0x00000002);
  }
  /**
   * <code>required int32 name = 2;</code>
   */
  public int getName() {
    return name_;
  }

  public static final int TYPE_FIELD_NUMBER = 3;
  private int type_;
  /**
   * <code>required int32 type = 3;</code>
   */
  public boolean hasType() {
    return ((bitField0_ & 0x00000004) == 0x00000004);
  }
  /**
   * <code>required int32 type = 3;</code>
   */
  public int getType() {
    return type_;
  }

  public static final int IS_VAR_FIELD_NUMBER = 4;
  private boolean isVar_;
  /**
   * <code>required bool is_var = 4;</code>
   */
  public boolean hasIsVar() {
    return ((bitField0_ & 0x00000008) == 0x00000008);
  }
  /**
   * <code>required bool is_var = 4;</code>
   */
  public boolean getIsVar() {
    return isVar_;
  }

  public static final int IS_CONST_FIELD_NUMBER = 5;
  private boolean isConst_;
  /**
   * <code>required bool is_const = 5;</code>
   */
  public boolean hasIsConst() {
    return ((bitField0_ & 0x00000010) == 0x00000010);
  }
  /**
   * <code>required bool is_const = 5;</code>
   */
  public boolean getIsConst() {
    return isConst_;
  }

  public static final int IS_LATEINIT_FIELD_NUMBER = 6;
  private boolean isLateinit_;
  /**
   * <code>required bool is_lateinit = 6;</code>
   */
  public boolean hasIsLateinit() {
    return ((bitField0_ & 0x00000020) == 0x00000020);
  }
  /**
   * <code>required bool is_lateinit = 6;</code>
   */
  public boolean getIsLateinit() {
    return isLateinit_;
  }

  public static final int INITIALIZER_FIELD_NUMBER = 7;
  private org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression initializer_;
  /**
   * <code>optional .org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression initializer = 7;</code>
   */
  public boolean hasInitializer() {
    return ((bitField0_ & 0x00000040) == 0x00000040);
  }
  /**
   * <code>optional .org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression initializer = 7;</code>
   */
  public org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression getInitializer() {
    return initializer_;
  }

  public static final int IS_CLASSDELEGATE_FIELD_NUMBER = 8;
  private boolean isClassDelegate_;
  /**
   * <code>required bool is_lateinit = 8;</code>
   */
  public boolean hasIsClassDelegete() {
    return ((bitField0_ & 0x8) == 0x8);
  }
  /**
   * <code>required bool is_classdelegate = 8;</code>
   */
  public boolean getIsClassDelegate() {
    return isClassDelegate_;
  }

  private void initFields() {
    base_ = org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase.getDefaultInstance();
    name_ = 0;
    type_ = 0;
    isVar_ = false;
    isConst_ = false;
    isLateinit_ = false;
    initializer_ = org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression.getDefaultInstance();
    isClassDelegate_ = false;
  }
  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    if (!hasBase()) {
      memoizedIsInitialized = 0;
      return false;
    }
    if (!hasName()) {
      memoizedIsInitialized = 0;
      return false;
    }
    if (!hasType()) {
      memoizedIsInitialized = 0;
      return false;
    }
    if (!hasIsVar()) {
      memoizedIsInitialized = 0;
      return false;
    }
    if (!hasIsConst()) {
      memoizedIsInitialized = 0;
      return false;
    }
    if (!hasIsLateinit()) {
      memoizedIsInitialized = 0;
      return false;
    }
    if (!getBase().isInitialized()) {
      memoizedIsInitialized = 0;
      return false;
    }
    if (hasInitializer()) {
      if (!getInitializer().isInitialized()) {
        memoizedIsInitialized = 0;
        return false;
      }
    }
    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(org.jetbrains.kotlin.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    getSerializedSize();
    if (((bitField0_ & 0x00000001) == 0x00000001)) {
      output.writeMessage(1, base_);
    }
    if (((bitField0_ & 0x00000002) == 0x00000002)) {
      output.writeInt32(2, name_);
    }
    if (((bitField0_ & 0x00000004) == 0x00000004)) {
      output.writeInt32(3, type_);
    }
    if (((bitField0_ & 0x00000008) == 0x00000008)) {
      output.writeBool(4, isVar_);
    }
    if (((bitField0_ & 0x00000010) == 0x00000010)) {
      output.writeBool(5, isConst_);
    }
    if (((bitField0_ & 0x00000020) == 0x00000020)) {
      output.writeBool(6, isLateinit_);
    }
    if (((bitField0_ & 0x00000040) == 0x00000040)) {
      output.writeMessage(7, initializer_);
    }
    if (((bitField0_ & 0x8) == 0x8)) {
      output.writeBool(8, isClassDelegate_);
    }
    output.writeRawBytes(unknownFields);
  }

  private int memoizedSerializedSize = -1;
  public int getSerializedSize() {
    int size = memoizedSerializedSize;
    if (size != -1) return size;

    size = 0;
    if (((bitField0_ & 0x00000001) == 0x00000001)) {
      size += org.jetbrains.kotlin.protobuf.CodedOutputStream
        .computeMessageSize(1, base_);
    }
    if (((bitField0_ & 0x00000002) == 0x00000002)) {
      size += org.jetbrains.kotlin.protobuf.CodedOutputStream
        .computeInt32Size(2, name_);
    }
    if (((bitField0_ & 0x00000004) == 0x00000004)) {
      size += org.jetbrains.kotlin.protobuf.CodedOutputStream
        .computeInt32Size(3, type_);
    }
    if (((bitField0_ & 0x00000008) == 0x00000008)) {
      size += org.jetbrains.kotlin.protobuf.CodedOutputStream
        .computeBoolSize(4, isVar_);
    }
    if (((bitField0_ & 0x00000010) == 0x00000010)) {
      size += org.jetbrains.kotlin.protobuf.CodedOutputStream
        .computeBoolSize(5, isConst_);
    }
    if (((bitField0_ & 0x00000020) == 0x00000020)) {
      size += org.jetbrains.kotlin.protobuf.CodedOutputStream
        .computeBoolSize(6, isLateinit_);
    }
    if (((bitField0_ & 0x00000040) == 0x00000040)) {
      size += org.jetbrains.kotlin.protobuf.CodedOutputStream
        .computeMessageSize(7, initializer_);
    }
    if (((bitField0_ & 0x8) == 0x8)) {
      size += org.jetbrains.kotlin.protobuf.CodedOutputStream
              .computeBoolSize(8, isClassDelegate_);
    }
    size += unknownFields.size();
    memoizedSerializedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @java.lang.Override
  protected java.lang.Object writeReplace()
      throws java.io.ObjectStreamException {
    return super.writeReplace();
  }

  public static org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable parseFrom(
      org.jetbrains.kotlin.protobuf.ByteString data)
      throws org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable parseFrom(
      org.jetbrains.kotlin.protobuf.ByteString data,
      org.jetbrains.kotlin.protobuf.ExtensionRegistryLite extensionRegistry)
      throws org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable parseFrom(byte[] data)
      throws org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable parseFrom(
      byte[] data,
      org.jetbrains.kotlin.protobuf.ExtensionRegistryLite extensionRegistry)
      throws org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return PARSER.parseFrom(input);
  }
  public static org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable parseFrom(
      java.io.InputStream input,
      org.jetbrains.kotlin.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return PARSER.parseFrom(input, extensionRegistry);
  }
  public static org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return PARSER.parseDelimitedFrom(input);
  }
  public static org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable parseDelimitedFrom(
      java.io.InputStream input,
      org.jetbrains.kotlin.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return PARSER.parseDelimitedFrom(input, extensionRegistry);
  }
  public static org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable parseFrom(
      org.jetbrains.kotlin.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return PARSER.parseFrom(input);
  }
  public static org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable parseFrom(
      org.jetbrains.kotlin.protobuf.CodedInputStream input,
      org.jetbrains.kotlin.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return PARSER.parseFrom(input, extensionRegistry);
  }

  public static Builder newBuilder() { return Builder.create(); }
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder(org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable prototype) {
    return newBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() { return newBuilder(this); }

  /**
   * Protobuf type {@code org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable}
   */
  public static final class Builder extends
      org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder<
        org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable, Builder>
      implements
      // @@protoc_insertion_point(builder_implements:org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable)
      org.jetbrains.kotlin.backend.common.serialization.proto.IrVariableOrBuilder {
    // Construct using org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private void maybeForceBuilderInitialization() {
    }
    private static Builder create() {
      return new Builder();
    }

    public Builder clear() {
      super.clear();
      base_ = org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase.getDefaultInstance();
      bitField0_ = (bitField0_ & ~0x00000001);
      name_ = 0;
      bitField0_ = (bitField0_ & ~0x00000002);
      type_ = 0;
      bitField0_ = (bitField0_ & ~0x00000004);
      isVar_ = false;
      bitField0_ = (bitField0_ & ~0x00000008);
      isConst_ = false;
      bitField0_ = (bitField0_ & ~0x00000010);
      isLateinit_ = false;
      bitField0_ = (bitField0_ & ~0x00000020);
      initializer_ = org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression.getDefaultInstance();
      bitField0_ = (bitField0_ & ~0x00000040);
      isClassDelegate_ = false;
      bitField0_ = (bitField0_ & ~0x8);
      return this;
    }

    public Builder clone() {
      return create().mergeFrom(buildPartial());
    }

    public org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable getDefaultInstanceForType() {
      return org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable.getDefaultInstance();
    }

    public org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable build() {
      org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable buildPartial() {
      org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable result = new org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
        to_bitField0_ |= 0x00000001;
      }
      result.base_ = base_;
      if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
        to_bitField0_ |= 0x00000002;
      }
      result.name_ = name_;
      if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
        to_bitField0_ |= 0x00000004;
      }
      result.type_ = type_;
      if (((from_bitField0_ & 0x00000008) == 0x00000008)) {
        to_bitField0_ |= 0x00000008;
      }
      result.isVar_ = isVar_;
      if (((from_bitField0_ & 0x00000010) == 0x00000010)) {
        to_bitField0_ |= 0x00000010;
      }
      result.isConst_ = isConst_;
      if (((from_bitField0_ & 0x00000020) == 0x00000020)) {
        to_bitField0_ |= 0x00000020;
      }
      result.isLateinit_ = isLateinit_;
      if (((from_bitField0_ & 0x00000040) == 0x00000040)) {
        to_bitField0_ |= 0x00000040;
      }
      result.initializer_ = initializer_;
      result.bitField0_ = to_bitField0_;
      return result;
    }

    public Builder mergeFrom(org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable other) {
      if (other == org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable.getDefaultInstance()) return this;
      if (other.hasBase()) {
        mergeBase(other.getBase());
      }
      if (other.hasName()) {
        setName(other.getName());
      }
      if (other.hasType()) {
        setType(other.getType());
      }
      if (other.hasIsVar()) {
        setIsVar(other.getIsVar());
      }
      if (other.hasIsConst()) {
        setIsConst(other.getIsConst());
      }
      if (other.hasIsLateinit()) {
        setIsLateinit(other.getIsLateinit());
      }
      if (other.hasInitializer()) {
        mergeInitializer(other.getInitializer());
      }
      setUnknownFields(
          getUnknownFields().concat(other.unknownFields));
      return this;
    }

    public final boolean isInitialized() {
      if (!hasBase()) {
        
        return false;
      }
      if (!hasName()) {
        
        return false;
      }
      if (!hasType()) {
        
        return false;
      }
      if (!hasIsVar()) {
        
        return false;
      }
      if (!hasIsConst()) {
        
        return false;
      }
      if (!hasIsLateinit()) {
        
        return false;
      }
      if (!getBase().isInitialized()) {
        
        return false;
      }
      if (hasInitializer()) {
        if (!getInitializer().isInitialized()) {
          
          return false;
        }
      }
      return true;
    }

    public Builder mergeFrom(
        org.jetbrains.kotlin.protobuf.CodedInputStream input,
        org.jetbrains.kotlin.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable) e.getUnfinishedMessage();
        throw e;
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase base_ = org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase.getDefaultInstance();
    /**
     * <code>required .org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase base = 1;</code>
     */
    public boolean hasBase() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required .org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase base = 1;</code>
     */
    public org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase getBase() {
      return base_;
    }
    /**
     * <code>required .org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase base = 1;</code>
     */
    public Builder setBase(org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase value) {
      if (value == null) {
        throw new NullPointerException();
      }
      base_ = value;

      bitField0_ |= 0x00000001;
      return this;
    }
    /**
     * <code>required .org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase base = 1;</code>
     */
    public Builder setBase(
        org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase.Builder builderForValue) {
      base_ = builderForValue.build();

      bitField0_ |= 0x00000001;
      return this;
    }
    /**
     * <code>required .org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase base = 1;</code>
     */
    public Builder mergeBase(org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase value) {
      if (((bitField0_ & 0x00000001) == 0x00000001) &&
          base_ != org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase.getDefaultInstance()) {
        base_ =
          org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase.newBuilder(base_).mergeFrom(value).buildPartial();
      } else {
        base_ = value;
      }

      bitField0_ |= 0x00000001;
      return this;
    }
    /**
     * <code>required .org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase base = 1;</code>
     */
    public Builder clearBase() {
      base_ = org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase.getDefaultInstance();

      bitField0_ = (bitField0_ & ~0x00000001);
      return this;
    }

    private int name_ ;
    /**
     * <code>required int32 name = 2;</code>
     */
    public boolean hasName() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>required int32 name = 2;</code>
     */
    public int getName() {
      return name_;
    }
    /**
     * <code>required int32 name = 2;</code>
     */
    public Builder setName(int value) {
      bitField0_ |= 0x00000002;
      name_ = value;
      
      return this;
    }
    /**
     * <code>required int32 name = 2;</code>
     */
    public Builder clearName() {
      bitField0_ = (bitField0_ & ~0x00000002);
      name_ = 0;
      
      return this;
    }

    private int type_ ;
    /**
     * <code>required int32 type = 3;</code>
     */
    public boolean hasType() {
      return ((bitField0_ & 0x00000004) == 0x00000004);
    }
    /**
     * <code>required int32 type = 3;</code>
     */
    public int getType() {
      return type_;
    }
    /**
     * <code>required int32 type = 3;</code>
     */
    public Builder setType(int value) {
      bitField0_ |= 0x00000004;
      type_ = value;
      
      return this;
    }
    /**
     * <code>required int32 type = 3;</code>
     */
    public Builder clearType() {
      bitField0_ = (bitField0_ & ~0x00000004);
      type_ = 0;
      
      return this;
    }

    private boolean isVar_ ;
    /**
     * <code>required bool is_var = 4;</code>
     */
    public boolean hasIsVar() {
      return ((bitField0_ & 0x00000008) == 0x00000008);
    }
    /**
     * <code>required bool is_var = 4;</code>
     */
    public boolean getIsVar() {
      return isVar_;
    }
    /**
     * <code>required bool is_var = 4;</code>
     */
    public Builder setIsVar(boolean value) {
      bitField0_ |= 0x00000008;
      isVar_ = value;
      
      return this;
    }
    /**
     * <code>required bool is_var = 4;</code>
     */
    public Builder clearIsVar() {
      bitField0_ = (bitField0_ & ~0x00000008);
      isVar_ = false;
      
      return this;
    }

    private boolean isConst_ ;
    /**
     * <code>required bool is_const = 5;</code>
     */
    public boolean hasIsConst() {
      return ((bitField0_ & 0x00000010) == 0x00000010);
    }
    /**
     * <code>required bool is_const = 5;</code>
     */
    public boolean getIsConst() {
      return isConst_;
    }
    /**
     * <code>required bool is_const = 5;</code>
     */
    public Builder setIsConst(boolean value) {
      bitField0_ |= 0x00000010;
      isConst_ = value;
      
      return this;
    }
    /**
     * <code>required bool is_const = 5;</code>
     */
    public Builder clearIsConst() {
      bitField0_ = (bitField0_ & ~0x00000010);
      isConst_ = false;
      
      return this;
    }

    private boolean isLateinit_ ;
    /**
     * <code>required bool is_lateinit = 6;</code>
     */
    public boolean hasIsLateinit() {
      return ((bitField0_ & 0x00000020) == 0x00000020);
    }
    /**
     * <code>required bool is_lateinit = 6;</code>
     */
    public boolean getIsLateinit() {
      return isLateinit_;
    }
    /**
     * <code>required bool is_lateinit = 6;</code>
     */
    public Builder setIsLateinit(boolean value) {
      bitField0_ |= 0x00000020;
      isLateinit_ = value;
      
      return this;
    }
    /**
     * <code>required bool is_lateinit = 6;</code>
     */
    public Builder clearIsLateinit() {
      bitField0_ = (bitField0_ & ~0x00000020);
      isLateinit_ = false;
      
      return this;
    }

    private boolean isClassDelegate_;
    /**
     * <code>required bool is_classdelegate = 8;</code>
     */
    public boolean hasIsClassDelegate() {
      return ((bitField0_ & 0x8) == 0x8);
    }
    /**
     * <code>required bool is_classdelegate = 8;</code>
     */
    public boolean getIsClassDelegate() {
      return isClassDelegate_;
    }
    /**
     * <code>required bool is_classdelegate = 8;</code>
     */
    public Builder setIsClassDelegate(boolean value) {
      bitField0_ |= 0x8;
      isClassDelegate_ = value;

      return this;
    }
    /**
     * <code>required bool is_lateinit = 6;</code>
     */
    public Builder clearIsClassDelegate() {
      bitField0_ = (bitField0_ & ~0x8);
      isClassDelegate_ = false;

      return this;
    }

    private org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression initializer_ = org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression.getDefaultInstance();
    /**
     * <code>optional .org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression initializer = 7;</code>
     */
    public boolean hasInitializer() {
      return ((bitField0_ & 0x00000040) == 0x00000040);
    }
    /**
     * <code>optional .org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression initializer = 7;</code>
     */
    public org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression getInitializer() {
      return initializer_;
    }
    /**
     * <code>optional .org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression initializer = 7;</code>
     */
    public Builder setInitializer(org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression value) {
      if (value == null) {
        throw new NullPointerException();
      }
      initializer_ = value;

      bitField0_ |= 0x00000040;
      return this;
    }
    /**
     * <code>optional .org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression initializer = 7;</code>
     */
    public Builder setInitializer(
        org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression.Builder builderForValue) {
      initializer_ = builderForValue.build();

      bitField0_ |= 0x00000040;
      return this;
    }
    /**
     * <code>optional .org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression initializer = 7;</code>
     */
    public Builder mergeInitializer(org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression value) {
      if (((bitField0_ & 0x00000040) == 0x00000040) &&
          initializer_ != org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression.getDefaultInstance()) {
        initializer_ =
          org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression.newBuilder(initializer_).mergeFrom(value).buildPartial();
      } else {
        initializer_ = value;
      }

      bitField0_ |= 0x00000040;
      return this;
    }
    /**
     * <code>optional .org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression initializer = 7;</code>
     */
    public Builder clearInitializer() {
      initializer_ = org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression.getDefaultInstance();

      bitField0_ = (bitField0_ & ~0x00000040);
      return this;
    }

    // @@protoc_insertion_point(builder_scope:org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable)
  }

  static {
    defaultInstance = new IrVariable(true);
    defaultInstance.initFields();
  }

  // @@protoc_insertion_point(class_scope:org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable)
}
