/*
 * The MIT License
 *
 * Copyright (c) 2011, CloudBees, Inc., Stephen Connolly
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package hudson.model;

import hudson.DescriptorExtensionList;
import hudson.ExtensionPoint;
import hudson.console.ConsoleAnnotator;
import hudson.console.ConsoleAnnotatorFactory;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Extensible property of {@link View}.
 *
 * <p>
 * {@link hudson.Plugin}s can extend this to define custom properties for {@link View}s.
 * {@link ViewProperty}s show up in the view configuration screen, and they are
 * persisted with the view object.
 *
 * <p>
 * Configuration screen should be defined in <code>config.jelly</code>.
 * Within this page, the {@link ViewProperty} instance is available as
 * the <code>instance</code> EL variable (while the <code>it</code> EL variable
 * refers to the {@link View}.
 *
 * @author Stephen Connolly
 * @since 1.406
 */
public class ViewProperty implements ReconfigurableDescribable<ViewProperty>, ExtensionPoint {
    /**
     * The view object that owns this property.
     * This value will be set by the core code.
     * Derived classes can expect this value to be always set.
     */
    protected transient View view;

    /*package*/ final void setView(View view) {
        this.view = view;
    }

    public ViewPropertyDescriptor getDescriptor() {
        return (ViewPropertyDescriptor)Hudson.getInstance().getDescriptorOrDie(getClass());
    }

    public static DescriptorExtensionList<ViewProperty,ViewPropertyDescriptor> all() {
        return Hudson.getInstance().<ViewProperty,ViewPropertyDescriptor>getDescriptorList(ViewProperty.class);
    }

    public ViewProperty reconfigure(StaplerRequest req, JSONObject form) throws Descriptor.FormException {
    	return form==null ? null : getDescriptor().newInstance(req, form);
    }
}
