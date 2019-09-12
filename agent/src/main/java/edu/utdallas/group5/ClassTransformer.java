package edu.utdallas.group5;



import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class ClassTransformer implements ClassFileTransformer {

    private final String packageName;

    public ClassTransformer(String packageName) {
        this.packageName = packageName;
    }

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (className.startsWith(packageName)){
            ClassReader cr = new ClassReader(classfileBuffer);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            ClassTransformVisitor ca = new ClassTransformVisitor(cw, className);
            cr.accept(ca, 0);

//            //
//            System.out.println(cn.name);
//            List<FieldNode> fieldList = cn.fields;
//            for (FieldNode fieldNode : fieldList) {
//                System.out.println("Field name: " + fieldNode.name);
//                System.out.println("Field desc: " + fieldNode.desc);
//                System.out.println("Filed value: " + fieldNode.value);
//                System.out.println("Filed access: " + fieldNode.access);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        //System.out.println("================================From package");
            return cw.toByteArray();
        }

        //System.out.println("================================From outside. Class name: " + className +  ", package: " + packageName);
        return classfileBuffer;
    }
}
