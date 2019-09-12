package edu.utdallas.group5;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.HashSet;


class MethodTransformVisitor extends MethodVisitor implements Opcodes {

    String mName;
    int line;
    private HashSet<Integer> visitedLines;
    private String className;
    long start,end;
   // TimeCache timeCache=new TimeCache();


    public MethodTransformVisitor(final MethodVisitor mv, String name, String className) {
        super(Opcodes.ASM5, mv);
        this.mName = name;
        this.className = className;
        visitedLines = new HashSet<>();
    }

    @Override
    public void visitLineNumber(int i, Label label) {
        this.line = i;
        record(i);
        super.visitLineNumber(i, label);
    }

    @Override
    public void visitLabel(Label label) {
        record(line);
        super.visitLabel(label);
    }

    @Override
    public void visitVarInsn(int opcode, int var){
        super.visitVarInsn(opcode,var);
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()+", opcode:"+opcode+", var:"+var);
    }

    @Override
    public void visitParameter(String name, int access){
        super.visitParameter(name,access);
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()+", name:"+name+", access:"+access);
    }

    @Override
    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index){
        super.visitLocalVariable(name, desc, signature, start, end, index);
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()+", name:"+name+", desc:"+desc+", signature:"+signature
        +", start:"+start+", end:"+end+", index:"+index);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc,boolean visible){
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()+", desc:"+desc);
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public AnnotationVisitor visitAnnotationDefault(){
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        return super.visitAnnotationDefault();
    }

    @Override
    public void visitMaxs(int maxStack,int maxLocals){
         super.visitMaxs(maxStack,maxLocals);
    }

    @Override
    public void visitCode(){
        super.visitCode();;
        System.out.println("visitor method begin:");
        start = System.currentTimeMillis();
        //timeCache.setStartTime(Thread.currentThread().getStackTrace()[1].getMethodName(),start);
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println("record time begin:");
    }

    @Override
    public void visitEnd(){
        super.visitEnd();
        end = System.currentTimeMillis();
        System.out.println("record time end:");
        System.out.println("start time:" + start+ "; end time:" + end+ "; Run Time:" + (end - start) + "(ms)");



    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name,String desc){
        super.visitFieldInsn(opcode, owner, name, desc);
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()+",opcode:"+opcode+",owner:"+owner+",name:"+name+",desc"+desc);
    }

    @Override
    public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack){
        super.visitFrame(type, nLocal, local, nStack, stack);
    }

    @Override
    public void visitIincInsn(int var,int increment){
        super.visitIincInsn(var,increment);
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()+", var:"+var+"increment:"+increment);
    }

    @Override
    public void visitInsn(int opcode){
        super.visitInsn(opcode);
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()+",opcode"+opcode);
    }

    @Override
    public void visitIntInsn(int opcode, int operand){
        super.visitIntInsn(opcode,operand);
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()+",opcode"+opcode+",operand"+operand);
    }

    @Override
    public void visitJumpInsn(int opcode,Label label){
       super.visitJumpInsn(opcode,label);
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()+",opcode"+opcode+",label"+label);
    }



    private void record(int line) {
        if (line == 0) return;
        String temp = className;
        mv.visitLdcInsn(temp);
        mv.visitLdcInsn(new Integer(line));
        mv.visitMethodInsn(INVOKESTATIC, "edu/utdallas/group5/CoverageManager", "newStatementCoverage", "(Ljava/lang/String;I)V", false);
    }

    public HashSet<Integer> getVisitedLines() {
        return visitedLines;
    }

    public void setVisitedLines(HashSet<Integer> visitedLines) {
        this.visitedLines = visitedLines;
    }
}