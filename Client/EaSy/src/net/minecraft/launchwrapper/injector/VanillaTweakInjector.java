/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.awt.Frame
 *  java.awt.Image
 *  java.awt.image.BufferedImage
 *  java.io.File
 *  java.io.IOException
 *  java.io.PrintStream
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Throwable
 *  java.nio.Buffer
 *  java.nio.ByteBuffer
 *  java.util.Arrays
 *  java.util.List
 *  javax.imageio.ImageIO
 *  org.lwjgl.opengl.Display
 */
package net.minecraft.launchwrapper.injector;

import java.awt.Frame;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import org.lwjgl.opengl.Display;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;

public class VanillaTweakInjector
implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        if (!"net.minecraft.client.Minecraft".equals((Object)name)) {
            return bytes;
        }
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 8);
        MethodNode mainMethod = null;
        for (MethodNode methodNode : classNode.methods) {
            if (!"main".equals((Object)methodNode.name)) continue;
            mainMethod = methodNode;
            break;
        }
        if (mainMethod == null) {
            return bytes;
        }
        FieldNode workDirNode = null;
        for (FieldNode fieldNode : classNode.fields) {
            String fileTypeDescriptor = Type.getDescriptor(File.class);
            if (!fileTypeDescriptor.equals((Object)fieldNode.desc) || (fieldNode.access & 8) != 8) continue;
            workDirNode = fieldNode;
            break;
        }
        MethodNode injectedMethod = new MethodNode();
        Label label = new Label();
        injectedMethod.visitLabel(label);
        injectedMethod.visitLineNumber(9001, label);
        injectedMethod.visitMethodInsn(184, "net/minecraft/launchwrapper/injector/VanillaTweakInjector", "inject", "()Ljava/io/File;");
        injectedMethod.visitFieldInsn(179, "net/minecraft/client/Minecraft", workDirNode.name, "Ljava/io/File;");
        mainMethod.instructions.insert(injectedMethod.instructions);
        ClassWriter writer = new ClassWriter(3);
        classNode.accept(writer);
        return writer.toByteArray();
    }

    public static File inject() {
        System.out.println("Turning of ImageIO disk-caching");
        ImageIO.setUseCache((boolean)false);
        VanillaTweakInjector.loadIconsOnFrames();
        System.out.println("Setting gameDir to: " + (Object)Launch.minecraftHome);
        return Launch.minecraftHome;
    }

    public static void loadIconsOnFrames() {
        try {
            File smallIcon = new File(Launch.assetsDir, "icons/icon_16x16.png");
            File bigIcon = new File(Launch.assetsDir, "icons/icon_32x32.png");
            System.out.println("Loading current icons for window from: " + (Object)smallIcon + " and " + (Object)bigIcon);
            Display.setIcon((ByteBuffer[])new ByteBuffer[]{VanillaTweakInjector.loadIcon(smallIcon), VanillaTweakInjector.loadIcon(bigIcon)});
            Frame[] frames = Frame.getFrames();
            if (frames != null) {
                List icons = Arrays.asList((Object[])new Image[]{ImageIO.read((File)smallIcon), ImageIO.read((File)bigIcon)});
                for (Frame frame : frames) {
                    try {
                        frame.setIconImages(icons);
                    }
                    catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ByteBuffer loadIcon(File iconFile) throws IOException {
        BufferedImage icon = ImageIO.read((File)iconFile);
        int[] rgb = icon.getRGB(0, 0, icon.getWidth(), icon.getHeight(), null, 0, icon.getWidth());
        ByteBuffer buffer = ByteBuffer.allocate((int)(4 * rgb.length));
        for (int color : rgb) {
            buffer.putInt(color << 8 | color >> 24 & 255);
        }
        buffer.flip();
        return buffer;
    }
}

