package org.hl7.tinkar.entity.forremoval;

import io.activej.bytebuf.ByteBuf;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.MutableIntList;
import org.eclipse.collections.impl.factory.primitive.IntLists;
import org.hl7.tinkar.common.id.*;
import org.hl7.tinkar.common.service.PrimitiveData;
import org.hl7.tinkar.component.*;
import org.hl7.tinkar.component.location.PlanarPoint;
import org.hl7.tinkar.component.location.SpatialPoint;
import org.hl7.tinkar.entity.*;
import org.hl7.tinkar.entity.graph.DiGraphEntity;
import org.hl7.tinkar.entity.graph.DiTreeEntity;
import org.hl7.tinkar.terms.ComponentWithNid;
import org.hl7.tinkar.terms.EntityFacade;
import org.hl7.tinkar.terms.EntityProxy;

import java.time.Instant;

import static java.nio.charset.StandardCharsets.UTF_8;

public class SemanticEntityVersionClass
        extends EntityVersionClass
        implements SemanticEntityVersion {

    protected final MutableList<Object> fields = Lists.mutable.empty();

    public static SemanticEntityVersion make(SemanticEntity semanticEntity, ByteBuf readBuf, byte formatVersion) {
        throw new UnsupportedOperationException();
        /*SemanticEntityVersionClass version = new SemanticEntityVersionClass();
        version.fill(semanticEntity, readBuf, formatVersion);
        return version;*/
    }

    public static SemanticEntityVersion make(SemanticEntity semanticEntity, SemanticVersion versionToCopy) {
        throw new UnsupportedOperationException();
        /*SemanticEntityVersionClass version = new SemanticEntityVersionClass();
        version.fill(semanticEntity, versionToCopy);
        version.fields.clear();
        for (Object obj : versionToCopy.fields()) {
            if (obj instanceof Boolean) {
                version.fields.add(obj);
            } else if (obj instanceof Float) {
                version.fields.add(obj);
            } else if (obj instanceof byte[]) {
                version.fields.add(obj);
            } else if (obj instanceof Integer) {
                version.fields.add(obj);
            } else if (obj instanceof String string) {
                version.fields.add(string.strip());
            } else if (obj instanceof Instant) {
                version.fields.add(obj);
            } else if (obj instanceof PlanarPoint) {
                version.fields.add(obj);
            } else if (obj instanceof SpatialPoint) {
                version.fields.add(obj);
            } else if (obj instanceof Component component) {
                version.fields.add(EntityProxy.make(EntityService.get().nidForComponent(component)));
            } else if (obj instanceof DiTreeDTO) {
                DiTree<Vertex> component = (DiTree<Vertex>) obj;
                version.fields.add(DiTreeEntity.make(component));
            } else if (obj instanceof DiGraphDTO) {
                DiGraph<Vertex> component = (DiGraph<Vertex>) obj;
                version.fields.add(DiGraphEntity.make(component));
            } else if (obj instanceof PublicIdSet) {
                PublicIdSet<PublicId> component = (PublicIdSet<PublicId>) obj;
                MutableIntSet idSet = IntSets.mutable.withInitialCapacity(component.size());
                component.forEach(publicId -> {
                    if (publicId == null) {
                        throw new IllegalStateException("PublicId cannot be null");
                    }
                    idSet.add(EntityService.get().nidForPublicId(publicId));
                });
                version.fields.add(IntIds.set.ofAlreadySorted(idSet.toSortedArray()));
            } else if (obj instanceof PublicIdList) {
                PublicIdList<PublicId> component = (PublicIdList<PublicId>) obj;
                MutableIntList idList = IntLists.mutable.withInitialCapacity(component.size());
                component.forEach(publicId -> {
                    idList.add(EntityService.get().nidForPublicId(publicId));
                });
                version.fields.add(IntIds.list.of(idList.toArray()));
            } else {
                throw new UnsupportedOperationException("Can't handle field conversion of type: " +
                        obj.getClass().getName());
            }
        }
        return version;*/
    }

    @Override
    public SemanticEntity entity() {
        return (SemanticEntity) super.entity();
    }

    @Override
    public SemanticEntity chronology() {
        return (SemanticEntity) super.chronology();
    }

    @Override
    public FieldDataType dataType() {
        return FieldDataType.SEMANTIC_VERSION;
    }

    @Override
    protected void writeVersionFields(ByteBuf writeBuf) {
        writeBuf.writeInt(fields.size());
        for (Object field : fields) {
            writeField(writeBuf, field);
        }
    }

    public static void writeField(ByteBuf writeBuf, Object field) {
        if (field instanceof Boolean) {
            writeBuf.writeByte(FieldDataType.BOOLEAN.token);
            writeBuf.writeBoolean((Boolean) field);
        } else if (field instanceof Float) {
            writeBuf.writeByte(FieldDataType.FLOAT.token);
            writeBuf.writeFloat((Float) field);
        } else if (field instanceof byte[] byteArray) {
            writeBuf.writeByte(FieldDataType.BYTE_ARRAY.token);
            writeBuf.writeInt(byteArray.length);
            writeBuf.write(byteArray);
        } else if (field instanceof Integer) {
            writeBuf.writeByte(FieldDataType.INTEGER.token);
            writeBuf.writeInt((Integer) field);
        } else if (field instanceof Instant instantField) {
            writeBuf.writeByte(FieldDataType.INSTANT.token);
            writeBuf.writeLong(instantField.getEpochSecond());
            writeBuf.writeInt(instantField.getNano());
        } else if (field instanceof String string) {
            writeBuf.writeByte(FieldDataType.STRING.token);
            byte[] bytes = string.getBytes(UTF_8);
            writeBuf.writeInt(bytes.length);
            writeBuf.write(bytes);
        } else if (field instanceof Concept concept) {
            writeBuf.writeByte(FieldDataType.CONCEPT.token);
            if (field instanceof ComponentWithNid) {
                writeBuf.writeInt(((ComponentWithNid) field).nid());
            } else {
                writeBuf.writeInt(EntityService.get().nidForComponent(concept));
            }
        } else if (field instanceof Semantic semantic) {
            writeBuf.writeByte(FieldDataType.SEMANTIC.token);
            if (field instanceof ComponentWithNid) {
                writeBuf.writeInt(((ComponentWithNid) field).nid());
            } else {
                writeBuf.writeInt(EntityService.get().nidForComponent(semantic));
            }
        } else if (field instanceof Pattern pattern) {
            writeBuf.writeByte(FieldDataType.PATTERN.token);
            if (field instanceof ComponentWithNid) {
                writeBuf.writeInt(((ComponentWithNid) field).nid());
            } else {
                writeBuf.writeInt(EntityService.get().nidForComponent(pattern));
            }
        } else if (field instanceof Entity entity) {
            writeBuf.writeByte(FieldDataType.IDENTIFIED_THING.token);
            writeBuf.writeInt(entity.nid());
        } else if (field instanceof EntityProxy proxy) {
            writeBuf.writeByte(FieldDataType.IDENTIFIED_THING.token);
            writeBuf.writeInt(proxy.nid());
        } else if (field instanceof Component component) {
            writeBuf.writeByte(FieldDataType.IDENTIFIED_THING.token);
            writeBuf.writeInt(EntityService.get().nidForComponent(component));
        } else if (field instanceof DiTreeEntity diTreeEntity) {
            writeBuf.writeByte(FieldDataType.DITREE.token);
            writeBuf.write(diTreeEntity.getBytes());
        } else if (field instanceof PlanarPoint point) {
            writeBuf.writeByte(FieldDataType.PLANAR_POINT.token);
            writeBuf.writeInt(point.x());
            writeBuf.writeInt(point.y());
        } else if (field instanceof SpatialPoint point) {
            writeBuf.writeByte(FieldDataType.SPATIAL_POINT.token);
            writeBuf.writeInt(point.x());
            writeBuf.writeInt(point.y());
            writeBuf.writeInt(point.z());
        } else if (field instanceof IntIdList ids) {
            writeBuf.writeByte(FieldDataType.COMPONENT_ID_LIST.token);
            writeBuf.writeInt(ids.size());
            ids.forEach(id -> writeBuf.writeInt(id));
        } else if (field instanceof IntIdSet ids) {
            writeBuf.writeByte(FieldDataType.COMPONENT_ID_SET.token);
            writeBuf.writeInt(ids.size());
            ids.forEach(id -> writeBuf.writeInt(id));
        } else if (field instanceof PublicIdList publicIdList) {
            MutableIntList nidList = IntLists.mutable.withInitialCapacity(publicIdList.size());
            publicIdList.forEach(publicId -> {
                nidList.add(PrimitiveData.get().nidForPublicId((PublicId) publicId));
            });
            writeBuf.writeByte(FieldDataType.COMPONENT_ID_LIST.token);
            writeBuf.writeInt(nidList.size());
            nidList.forEach(id -> writeBuf.writeInt(id));
        } else if (field instanceof PublicIdSet publicIdSet) {
            MutableIntList nidSet = IntLists.mutable.withInitialCapacity(publicIdSet.size());
            publicIdSet.forEach(publicId -> {
                nidSet.add(PrimitiveData.get().nidForPublicId((PublicId) publicId));
            });
            writeBuf.writeByte(FieldDataType.COMPONENT_ID_SET.token);
            writeBuf.writeInt(nidSet.size());
            nidSet.forEach(id -> writeBuf.writeInt(id));
        } else {
            throw new UnsupportedOperationException("Can't handle field write of type: " +
                    field.getClass().getName());
        }
    }

    @Override
    protected void finishVersionFill(ByteBuf readBuf, byte formatVersion) {
        fields.clear();
        int fieldCount = readBuf.readInt();
        for (int i = 0; i < fieldCount; i++) {
            FieldDataType dataType = FieldDataType.fromToken(readBuf.readByte());
            fields.add(readDataType(readBuf, dataType, formatVersion));
        }
    }

    public static Object readDataType(ByteBuf readBuf, FieldDataType dataType, byte formatVersion) {
        switch (dataType) {
            case BOOLEAN:
                return readBuf.readBoolean();
            case FLOAT:
                return readBuf.readFloat();
            case BYTE_ARRAY: {
                int length = readBuf.readInt();
                byte[] bytes = new byte[length];
                readBuf.read(bytes);
                return bytes;
            }
            case INTEGER:
                return readBuf.readInt();
            case STRING: {
                int length = readBuf.readInt();
                byte[] bytes = new byte[length];
                readBuf.read(bytes);
                return new String(bytes, UTF_8);
            }
            case DITREE:
                return DiTreeEntity.make(readBuf, formatVersion);
            case DIGRAPH:
                return DiGraphEntity.make(readBuf, formatVersion);
            case CONCEPT: {
                int nid = readBuf.readInt();
                return EntityProxy.Concept.make(nid);
            }
            case SEMANTIC: {
                int nid = readBuf.readInt();
                return EntityProxy.Semantic.make(nid);
            }
            case PATTERN: {
                int nid = readBuf.readInt();
                return EntityProxy.Pattern.make(nid);
            }
            case IDENTIFIED_THING:
                return EntityProxy.make(readBuf.readInt());
            case INSTANT:
                return Instant.ofEpochSecond(readBuf.readLong(), readBuf.readInt());
            case PLANAR_POINT:
                return new PlanarPoint(readBuf.readInt(), readBuf.readInt());
            case SPATIAL_POINT:
                return new SpatialPoint(readBuf.readInt(), readBuf.readInt(), readBuf.readInt());
            case COMPONENT_ID_LIST:
                return IntIds.list.of(readIntArray(readBuf));
            case COMPONENT_ID_SET:
                return IntIds.set.of(readIntArray(readBuf));
            default:
                throw new UnsupportedOperationException("Can't handle field read of type: " +
                        dataType);
        }
    }

    static protected int[] readIntArray(ByteBuf readBuf) {
        int size = readBuf.readInt();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = readBuf.readInt();
        }
        return array;
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException();
        /*StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append(Entity.getStamp(stampNid).describe());
        PatternEntity pattern = Entity.getFast(this.chronology().patternNid());
        if (pattern instanceof PatternEntity patternEntity) {
            // TODO get proper version after relative position computer available.
            // Maybe put stamp coordinate on thread, or relative position computer on thread
            PatternEntityVersion patternEntityVersion = (PatternEntityVersion) patternEntity.versions.get(0);
            sb.append("\n");
            for (int i = 0; i < fields.size(); i++) {
                sb.append("Field ");
                sb.append((i + 1));
                sb.append(": [");
                StringBuilder fieldStringBuilder = new StringBuilder();

                Object field = fields.get(i);
                if (i < patternEntityVersion.fieldDefinitions().size()) {
                    FieldDefinitionForEntity fieldDefinition = patternEntityVersion.fieldDefinitions().get(i);
                    fieldStringBuilder.append(PrimitiveData.text(fieldDefinition.meaningNid));
                } else {
                    fieldStringBuilder.append("Size error @ " + i);
                }
                fieldStringBuilder.append(": ");
                if (field instanceof EntityFacade entity) {
                    fieldStringBuilder.append(PrimitiveData.text(entity.nid()));
                } else if (field instanceof String string) {
                    fieldStringBuilder.append(string);
                } else if (field instanceof Instant instant) {
                    fieldStringBuilder.append(DateTimeUtil.format(instant));
                } else if (field instanceof IntIdList intIdList) {
                    if (intIdList.size() == 0) {
                        fieldStringBuilder.append("ø");
                    } else {
                        for (int j = 0; j < intIdList.size(); j++) {
                            if (j > 0) {
                                fieldStringBuilder.append(", ");
                            }
                            fieldStringBuilder.append(PrimitiveData.text(intIdList.get(j)));
                        }
                    }
                } else if (field instanceof IntIdSet intIdSet) {
                    if (intIdSet.size() == 0) {
                        fieldStringBuilder.append("ø");
                    } else {
                        int[] idSetArray = intIdSet.toArray();
                        for (int j = 0; j < idSetArray.length; j++) {
                            if (j > 0) {
                                fieldStringBuilder.append(", ");
                            }
                            fieldStringBuilder.append(PrimitiveData.text(idSetArray[j]));
                        }
                    }
                } else {
                    fieldStringBuilder.append(field);
                }
                String fieldString = fieldStringBuilder.toString();
                if (fieldString.contains("\n")) {
                    sb.append("\n");
                    sb.append(fieldString);
                } else {
                    sb.append(fieldString);
                }
                sb.append("]\n");

            }
        } else {
            sb.append("Bad pattern: ");
            sb.append(PrimitiveData.text(pattern.nid()));
            sb.append("; ");
            for (int i = 0; i < fields.size(); i++) {
                Object field = fields.get(i);
                if (i > 0) {
                    sb.append("; ");
                }
                if (field instanceof EntityFacade entity) {
                    sb.append("Entity: ");
                    sb.append(PrimitiveData.text(entity.nid()));
                } else if (field instanceof String string) {
                    sb.append("String: ");
                    sb.append(string);
                } else if (field instanceof Instant instant) {
                    sb.append("Instant: ");
                    sb.append(DateTimeUtil.format(instant));
                } else if (field instanceof Long aLong) {
                    sb.append("Long: ");
                    sb.append(DateTimeUtil.format(aLong));
                } else if (field instanceof IntIdList intIdList) {
                    sb.append(field.getClass().getSimpleName());
                    sb.append(": ");
                    if (intIdList.size() == 0) {
                        sb.append("ø, ");
                    } else {
                        for (int j = 0; j < intIdList.size(); j++) {
                            if (j > 0) {
                                sb.append(", ");
                            }
                            sb.append(PrimitiveData.text(intIdList.get(j)));
                        }
                    }
                } else if (field instanceof IntIdSet intIdSet) {
                    sb.append(field.getClass().getSimpleName());
                    sb.append(": ");
                    if (intIdSet.size() == 0) {
                        sb.append("ø, ");
                    } else {
                        int[] idSetArray = intIdSet.toArray();
                        for (int j = 0; j < idSetArray.length; j++) {
                            if (j > 0) {
                                sb.append(", ");
                            }
                            sb.append(PrimitiveData.text(idSetArray[j]));
                        }
                    }
                } else {
                    sb.append(field.getClass().getSimpleName());
                    sb.append(": ");
                    sb.append(field);
                }
            }
        }

        sb.append("]}");

        return sb.toString();*/
    }

    @Override
    public EntityFacade referencedComponent() {
        return this.chronology().referencedComponent();
    }

    @Override
    public int referencedComponentNid() {
        return this.chronology().referencedComponentNid();
    }

    @Override
    public PatternEntity pattern() {
        return Entity.getFast(patternNid());
    }

    @Override
    public int patternNid() {
        return this.chronology().patternNid();
    }

    @Override
    public FieldDataType fieldDataType(int fieldIndex) {
        return FieldDataType.getFieldDataType(fields.get(fieldIndex));
    }

    @Override
    public ImmutableList<Object> fields() {
        return fields.toImmutable();
    }

    @Override
    public ImmutableList<Field> fields(PatternEntityVersion patternVersion) {
        Field[] fieldArray = new Field[fields.size()];
        for (int i = 0; i < fieldArray.length; i++) {
            Object value = fields.get(i);
            FieldDefinitionForEntity fieldDef = patternVersion.fieldDefinitions().get(i);
            if (fieldDef.narrativeOptional().isPresent()) {
                fieldArray[i] = new FieldRecord(value, fieldDef.narrativeOptional().get(), fieldDef.dataTypeNid(), fieldDef.purposeNid(), fieldDef.meaningNid(),
                        this);
            } else {
                fieldArray[i] = new FieldRecord(value, null, fieldDef.dataTypeNid(), fieldDef.purposeNid(), fieldDef.meaningNid(),
                        this);
            }
        }
        return Lists.immutable.of(fieldArray);
    }
}