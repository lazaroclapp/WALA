/*******************************************************************************
 * Copyright (c) 2002 - 2006 IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package com.ibm.wala.ipa.callgraph.impl;

import java.util.Iterator;
import java.util.Set;

import com.ibm.wala.ipa.callgraph.Entrypoint;
import com.ibm.wala.ipa.callgraph.Entrypoints;
import com.ibm.wala.util.collections.HashSetFactory;

/**
 * This class represents the union of two sets of entryponts.
 * 
 * @author sfink
 *
 */
public class ComposedEntrypoints implements Entrypoints {

  private Set<Entrypoint> entrypoints = HashSetFactory.make();
  
  public ComposedEntrypoints(Entrypoints A, Entrypoints B) {
    for (Iterator<? extends Entrypoint> it = A.iterator(); it.hasNext(); ) {
      entrypoints.add(it.next());
    }
    for (Iterator<? extends Entrypoint> it = B.iterator(); it.hasNext(); ) {
      entrypoints.add(it.next());
    }
  }

  /* (non-Javadoc)
   * @see com.ibm.wala.ipa.callgraph.Entrypoints#iterator()
   */
  public Iterator<Entrypoint> iterator() {
    return entrypoints.iterator();
  }

}
