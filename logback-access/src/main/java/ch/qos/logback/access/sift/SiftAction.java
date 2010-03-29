/**
 * Logback: the reliable, generic, fast and flexible logging framework.
 * Copyright (C) 1999-2009, QOS.ch. All rights reserved.
 *
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 *
 *   or (per the licensee's choosing)
 *
 * under the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation.
 */
package ch.qos.logback.access.sift;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import ch.qos.logback.core.joran.action.Action;
import ch.qos.logback.core.joran.event.InPlayListener;
import ch.qos.logback.core.joran.event.SaxEvent;
import ch.qos.logback.core.joran.spi.ActionException;
import ch.qos.logback.core.joran.spi.InterpretationContext;

public class SiftAction  extends Action implements InPlayListener {
  List<SaxEvent> seList;
  
  @Override
  public void begin(InterpretationContext ic, String name, Attributes attributes)
      throws ActionException {
    seList = new ArrayList<SaxEvent>();
    ic.addInPlayListener(this);
  }

  @Override
  public void end(InterpretationContext ic, String name) throws ActionException {
    ic.removeInPlayListener(this);
    Object o = ic.peekObject();
    if (o instanceof SiftingAppender) {
      SiftingAppender siftingAppender = (SiftingAppender) o; 
      AppenderFactory appenderFactory = new AppenderFactory(seList, siftingAppender.getDiscriminatorKey());
      siftingAppender.setAppenderFactory(appenderFactory);
    }
  }

  public void inPlay(SaxEvent event) {
    seList.add(event);
  }

  public List<SaxEvent> getSeList() {
    return seList;
  }

    


}
